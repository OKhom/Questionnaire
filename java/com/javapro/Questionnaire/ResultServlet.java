package com.javapro.Questionnaire;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicIntegerArray;

@WebServlet(name = "ResultServlet", value = "/result")
public class ResultServlet extends HttpServlet {
    final AtomicIntegerArray result1 = new AtomicIntegerArray(answers1.length);
    final AtomicIntegerArray result2 = new AtomicIntegerArray(answers2.length);
    static final String question1 = "What color you like better?";
    static final String question2 = "What season you like better?";
    static final String[] answers1 = {"Red", "Blue", "Green"};
    static final String[] answers2 = {"Spring", "Summer", "Autumn", "Winter"};
    static final int cellWidth = 100;
    static final String TEMPLATE = "<html>" +
            "\n<head>\n\t<title>Questionnaire</title>\n</head>" +
            "\n<body>\n\t<h1>Questionnaire Results</h1>" +
            "\n\t%s\n</body>\n</html>\n";

    public static String getQuestion1() {
        return question1;
    }

    public static String getQuestion2() {
        return question2;
    }

    public static String[] getAnswers1() {
        return answers1;
    }

    public static String[] getAnswers2() {
        return answers2;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession userSession = request.getSession();
        String userLogin = (String) userSession.getAttribute("userLogin");
        int[] userResult1 = (int[])userSession.getAttribute("userResult1");
        int[] userResult2 = (int[])userSession.getAttribute("userResult2");

        try {
            int a1 = Integer.parseInt(request.getParameter("question1"));
            int a2 = Integer.parseInt(request.getParameter("question2"));
            userResult1[a1]++;
            userResult2[a2]++;
            result1.incrementAndGet(a1);
            result2.incrementAndGet(a2);
        } catch (NumberFormatException nfe) {
            response.sendRedirect("index.jsp");
        }

        StringBuilder resultTable = new StringBuilder();
// Building Table with First Question Answers
        StringBuilder tableHeader = new StringBuilder();
        StringBuilder userAnswer = new StringBuilder();
        StringBuilder totalAnswer = new StringBuilder();
        for (int i = 0; i < answers1.length; i++) {
            tableHeader.append("<th width=\"").append(cellWidth).append("px\">").append(answers1[i]).append("</th>");
            userAnswer.append("<td align=\"center\">").append(userResult1[i]).append("</td>");
            totalAnswer.append("<td align=\"center\">").append(result1.get(i)).append("</td>");
        }
        resultTable.append("\t<table border=\"1\">\n");
        resultTable.append("\t\t<caption>Question 1. ").append(question1).append("</caption>\n");
        resultTable.append("\t\t<tr>").append("<th width=\"").append(cellWidth).append("px\">").append("</th>");
        resultTable.append(tableHeader).append("</tr>\n");
        resultTable.append("\t\t<tr>").append("<td align=\"center\">").append(userLogin).append("</td>");
        resultTable.append(userAnswer).append("</tr>\n");
        resultTable.append("\t\t<tr>").append("<td align=\"center\">").append("Total").append("</td>");
        resultTable.append(totalAnswer).append("</tr>\n");
        resultTable.append("\t<table>\n\t<br>\n");
// Building Table with Second Question Answers
        tableHeader = new StringBuilder();
        userAnswer = new StringBuilder();
        totalAnswer = new StringBuilder();
        for (int i = 0; i < answers2.length; i++) {
            tableHeader.append("<th width=\"").append(cellWidth).append("px\">").append(answers2[i]).append("</th>");
            userAnswer.append("<td align=\"center\">").append(userResult2[i]).append("</td>");
            totalAnswer.append("<td align=\"center\">").append(result2.get(i)).append("</td>");
        }
        resultTable.append("\t<table border=\"1\">\n");
        resultTable.append("\t\t<caption>Question 2. ").append(question2).append("</caption>\n");
        resultTable.append("\t\t<tr>").append("<th width=\"").append(cellWidth).append("px\">").append("</th>");
        resultTable.append(tableHeader).append("</tr>\n");
        resultTable.append("\t\t<tr>").append("<td align=\"center\">").append(userLogin).append("</td>");
        resultTable.append(userAnswer).append("</tr>\n");
        resultTable.append("\t\t<tr>").append("<td align=\"center\">").append("Total").append("</td>");
        resultTable.append(totalAnswer).append("</tr>\n");
        resultTable.append("\t<table>\n\t<br>");
        resultTable.append("Click <a href=/index.jsp>here</a> to vote again</br>");

        PrintWriter pw = response.getWriter();
        pw.printf(String.format(TEMPLATE, resultTable));
    }
}
