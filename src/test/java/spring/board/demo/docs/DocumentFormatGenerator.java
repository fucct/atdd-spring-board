package spring.board.demo.docs;

import static org.springframework.restdocs.snippet.Attributes.*;

import org.springframework.restdocs.snippet.Attributes;

public interface DocumentFormatGenerator {

    static Attributes.Attribute getPasswordFormat() {
        return key("format").value("1글자 이상");
    }
}

