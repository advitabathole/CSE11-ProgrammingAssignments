import java.util.Scanner;

public class GradeCalculator {
    public static void main (String[] args) {
        //create scanner
        Scanner scanner = new Scanner(System.in);


        double totalPaScore = 0;

        //PA scores
        for (int i = 0; i < 9; i++){
            if(!scanner.hasNextDouble()) { invalid(); return; }
            double original = scanner.nextDouble();
            if(!scanner.hasNextDouble()) { invalid(); return; }
            double resub = scanner.nextDouble();

            if(original < 0 || original > 100 || resub < 0 || resub > 100) {
                invalid(); return;
            }

            double finalScore = Math.max((original + resub)/2, original);
            totalPaScore += finalScore;
        }


        //quiz scores
        double totalQuizScore = 0; 
        for(int i = 0; i < 3; i++) {
            if(!scanner.hasNextDouble()) { invalid(); return; }
            double quiz = scanner.nextDouble();
            if(!scanner.hasNextDouble()) { invalid(); return; }
            double makeup = scanner.nextDouble();

            if (quiz < 0 || quiz > 100 || makeup < 0 || makeup > 100) {
                invalid(); return;
            }
            double finalScore =  Math.max(quiz, makeup);
            totalQuizScore += finalScore;
        }
        
    //attendance
    if(!scanner.hasNextInt()) { invalid(); return; }
    int attendance = scanner.nextInt();
    if(attendance < 0 || attendance > 16) {
        invalid(); return;
    }
    int finalAttendance = Math.min(attendance, 10);
    double attendancePct = (finalAttendance / 10.0) * 100;

    //midterm
    if(!scanner.hasNextDouble()) { invalid(); return; }
    double midterm = scanner.nextDouble();
    if(midterm < 0 || midterm > 100) {invalid(); return;}

    //final exam
    if(!scanner.hasNextDouble()) { invalid(); return; }
    double finalExam1 = scanner.nextDouble();
    if(!scanner.hasNextDouble()) { invalid(); return; }
    double finalExam2 = scanner.nextDouble();
    if (finalExam1 < 0 || finalExam1 > 100 || finalExam2 < 0 || finalExam2 > 100) {
        invalid(); return;
    }

    //replace midterm if finalExam1 > midterm
    double midtermFinal = Math.max(midterm, finalExam1);

    //pa and quiz percentages
    double paPct = (totalPaScore / 810.0) * 100;
    double quizPct = totalQuizScore / 3.0;

    // final grade
    double endScore = attendancePct * 0.10 + paPct * 0.40 + quizPct * 0.10 + midtermFinal * 0.15 + finalExam2 * 0.25;

    //
    String letterGrade;
    if (endScore >= 90) {
        letterGrade = "A";
    } else if (endScore >= 80) {
        letterGrade = "B";
    } else if (endScore >= 70) {
        letterGrade = "C";
    } else if (endScore >= 60) {
        letterGrade = "D";
    } else {
        letterGrade = "F";
    }

    //print results
    System.out.printf("%.2f\n", endScore);
    System.out.println(letterGrade);

    //close scanner
    scanner.close();
    }

    private static void invalid() {
        System.out.println("invalid input");
    }

}