package quickstart.domains.functionmap;

import io.github.gfrmoretti.functionmap.FunctionMapper;

import java.util.Optional;

public class EmailToStringFunctionMapper implements FunctionMapper<Email, String> {
    @Override
    public Optional<String> mapValue(Email value) {
        return Optional.of(value.getFormattedEmail());
    }

    @Override
    public Optional<Email> inverseMapValue(String value) {
        if (value == null)
            return Optional.empty();
        return Optional.of(new Email(value));
    }
}
