package utils.service;

public class LowerCaseQuestionComparer implements IQuestionComparer {

    @Override
    public boolean equal(final String first, final String last) {
        return first
                .toLowerCase()
                .compareTo(last.toLowerCase()) == 0;
    }

}
