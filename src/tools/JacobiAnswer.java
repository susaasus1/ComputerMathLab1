package tools;

import java.text.DecimalFormat;

public class JacobiAnswer {
    private Matrix roots;
    private Matrix errors;
    private int iterations;

    public JacobiAnswer(Matrix roots, Matrix errors, int iterations) {
        this.roots = roots;
        this.errors = errors;
        this.iterations = iterations;
    }

    public String toString() {
        String answer="Приближенное решение задачи:\n";
        for (int i = 0; i < roots.getRow(); ++i) {
            String dou= new DecimalFormat("#0.00000").format(roots.get(i,0));
            answer+="x"+i+"="+dou+"\n";
        }
        answer+="Абсолютное отклонение = ";
        answer+= new DecimalFormat("#0.00000").format(errors.getAbsMax())+"\n";
        answer+="Количество произведенных итераций: ";
        answer+=iterations;
        return answer;
    }

}
