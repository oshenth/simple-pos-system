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
        try {
            Document doc = new Document();
            String path = "bill.pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            doc.add(new Paragraph("POS BILL\n\n"));

            PdfPTable t = new PdfPTable(3);
            t.addCell("Item");
            t.addCell("Qty");
            t.addCell("Total");

            double sum = 0;
            for (CartItem i : cart.values()) {
                t.addCell(i.name);
                t.addCell(String.valueOf(i.qty));
                t.addCell(String.valueOf(i.total()));
                sum += i.total();
            }
            doc.add(t);
            doc.add(new Paragraph("\nGrand Total: " + sum));
            doc.close();

            saveToDB(sum, Files.readAllBytes(Paths.get(path)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveToDB(double total, byte[] pdf)
            throws Exception {

        PreparedStatement ps =
            DB.getConnection().prepareStatement(
            "INSERT INTO sales(sale_date,total,pdf) VALUES(NOW(),?,?)"
        );
        ps.setDouble(1, total);
        ps.setBytes(2, pdf);
        ps.executeUpdate();
    }
}
