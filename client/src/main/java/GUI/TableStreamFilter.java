package GUI;

import exceptions.IncorrectInputException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.Locale;

public class TableStreamFilter {

    public void filter(String statement, MyTableModel model) throws IncorrectInputException {
        if (model.getRowCount() == 1 && model.getValueAt(0, 0) == null)
            return;
        model.setData(Arrays.stream(model.getData())
                .filter(row -> exStatement(row, statement, model))
                .toArray(Object[][]::new));

    }

    private boolean exStatement(Object[] row, String statement, MyTableModel model) throws IncorrectInputException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine eng = factory.getEngineByName("nashorn");
        try {
            statement = statement.toLowerCase(Locale.ENGLISH);
            setVars(eng, row, statement, model);
            return (boolean) eng.eval(statement);
        } catch (Exception e) {
            throw new IncorrectInputException("Incorrect statement");
        }
    }

    private void setVars(ScriptEngine eng, Object[] row, String statement, MyTableModel model) throws ScriptException {
        if (statement.contains("id"))
            eng.eval(model.getHeader()[0].toString().toLowerCase(Locale.ENGLISH) + " = " + row[0]);
        if (statement.contains("name"))
            eng.eval(model.getHeader()[1].toString().toLowerCase(Locale.ENGLISH) + " = '" + row[1] + "'");
        if (statement.contains("x"))
            eng.eval(model.getHeader()[2].toString().toLowerCase(Locale.ENGLISH) + " = " + row[2]);
        if (statement.contains("y"))
            eng.eval(model.getHeader()[3].toString().toLowerCase(Locale.ENGLISH) + " = " + row[3]);
//        eng.eval(model.getHeader()[4].toString().toLowerCase(Locale.ENGLISH) +
//                " = new Date(" + ((MyDate) row[4]).getDate().getTime() + ")");
        if (statement.contains("weight"))
            eng.eval(model.getHeader()[5].toString().toLowerCase(Locale.ENGLISH) + " = " + row[5]);
        if (statement.contains("age")) {
            if (row[6] == null)
                eng.eval(model.getHeader()[6].toString().toLowerCase(Locale.ENGLISH) + " = null");
            else
                eng.eval(model.getHeader()[6].toString().toLowerCase(Locale.ENGLISH) + " = " + row[6]);
        }
        if (statement.contains("color"))
            eng.eval(model.getHeader()[7].toString().toLowerCase(Locale.ENGLISH) + " = '" +
                    row[7].toString().toLowerCase(Locale.ENGLISH) + "'");
        if (statement.contains("character"))
            eng.eval(model.getHeader()[8].toString().toLowerCase(Locale.ENGLISH) + " = '" +
                    row[8].toString().toLowerCase(Locale.ENGLISH) + "'");
        if (statement.contains("eyes_count"))
            eng.eval(model.getHeader()[9].toString().toLowerCase(Locale.ENGLISH) + " = " + row[9]);
        if (statement.contains("owner"))
            eng.eval(model.getHeader()[10].toString().toLowerCase(Locale.ENGLISH) + " = '" + row[10] + "'");
    }

}
