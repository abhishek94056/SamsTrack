package com.abhishek.SamsTrack.report;


import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abhishek.SamsTrack.entity.AttendanceRecord;
import com.abhishek.SamsTrack.entity.Student;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class AttendancePdfReportService {

    public byte[] generateAttendanceReport(List<AttendanceRecord> records) {

        try {
            //Create PDF document
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            //Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Student Attendance Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            //Table with columns
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setWidths(new float[] { 1.5f, 3f, 3f, 3f, 2f, 2f });

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

            addTableHeader(table, "Date", headerFont);
            addTableHeader(table, "Time", headerFont);
            addTableHeader(table, "Subject", headerFont);
            addTableHeader(table, "Faculty", headerFont);
            addTableHeader(table, "Student Name", headerFont);
            addTableHeader(table, "Email", headerFont);

            //Fill table data
            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            for (AttendanceRecord record : records) {
                for (Student student : record.getStudents()) {

                    table.addCell(new Phrase(record.getDate(), dataFont));
                    table.addCell(new Phrase(record.getTime(), dataFont));
                    table.addCell(new Phrase(record.getSubject().getName(), dataFont));
                    table.addCell(new Phrase(record.getUser().getFirstName(), dataFont));
                    table.addCell(new Phrase(student.getName(), dataFont));
                    table.addCell(new Phrase(student.getEmail(), dataFont));
                }
            }

            document.add(table);
            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate attendance PDF report", e);
        }
    }

    //Helper method for table header
    private void addTableHeader(PdfPTable table, String headerTitle, Font font) {
        PdfPCell header = new PdfPCell();
        header.setPhrase(new Phrase(headerTitle, font));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setPadding(5);
        table.addCell(header);
    }  
}
