package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import db.DB;
import model.CartItem;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.Map;

public class PDFGenerator {

    public static void generate(Map<String, CartItem> cart) {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false); // start transaction

            // 1. Insert new row in invoice_number to get next invoice number
            String invoiceNumber = insertAndGetInvoiceNumber(conn);

            // 2. Set PDF file name
            String fileName = "Sales Bill " + invoiceNumber + ".pdf";

            // 3. Create PDF
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();

            doc.add(new Paragraph("SALES BILL\nInvoice No: " + invoiceNumber + "\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));

            PdfPTable t = new PdfPTable(3);
            t.setWidthPercentage(100);
            t.setWidths(new float[]{5, 2, 3});

            // Table header
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Item", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(cell);

            cell = new PdfPCell(new Phrase("Qty", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(cell);

            cell = new PdfPCell(new Phrase("Total", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(cell);

            // Table data
            double sum = 0;
            for (CartItem i : cart.values()) {
                t.addCell(i.name);
                t.addCell(String.valueOf(i.qty));
                t.addCell(String.format("%.2f", i.total()));
                sum += i.total();
            }

            doc.add(t);
            doc.add(new Paragraph("\nGrand Total: Rs. " + String.format("%.2f", sum),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));

            doc.close();

            // 4. Save PDF to sales table
            saveToDB(conn, invoiceNumber, sum, Files.readAllBytes(Paths.get(fileName)));

            conn.commit(); // commit transaction

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // Insert a row in invoice_number and return the new invoice number
    private static String insertAndGetInvoiceNumber(Connection conn) throws SQLException {
        // 1. Insert a new row (MySQL style)
        PreparedStatement psInsert = conn.prepareStatement(
                "INSERT INTO invoice_number () VALUES ()", Statement.RETURN_GENERATED_KEYS
        );
        psInsert.executeUpdate();

        // 2. Get generated key (auto-increment invoice number)
        ResultSet rs = psInsert.getGeneratedKeys();
        String invoiceNumber = null;
        if (rs.next()) {
            invoiceNumber = String.format("%04d", rs.getInt(1)); // format as 4-digit
        }
        rs.close();
        psInsert.close();

        return invoiceNumber;
    }

    private static void saveToDB(Connection conn, String invoiceNumber, double total, byte[] pdf) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO sales(invoice_no, sale_date, total, pdf) VALUES(?, NOW(), ?, ?)"
        );
        ps.setString(1, invoiceNumber);
        ps.setDouble(2, total);
        ps.setBytes(3, pdf);
        ps.executeUpdate();
        ps.close();
    }
}