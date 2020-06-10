package spring.board.demo.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static spring.board.demo.docs.ApiDocumentUtils.*;
import static spring.board.demo.docs.DocumentFormatGenerator.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserDocumentation {

    public static RestDocumentationResultHandler create() {
        return document("accounts/create",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("The account's Id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("The account's name"),
                fieldWithPath("password").type(JsonFieldType.STRING)
                    .description("The account's password")
            ),
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("The account's no")
            )
        );
    }

    public static RestDocumentationResultHandler get() {
        return document("accounts/get",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("The account's no"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("The account's id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("The account's name"),
                fieldWithPath("articles").type(JsonFieldType.ARRAY)
                    .description("The account's articles")
                    .optional(),
                fieldWithPath("comments").type(JsonFieldType.ARRAY)
                    .description("The account's comments")
                    .optional()
            )
        );
    }

    public static RestDocumentationResultHandler update() {
        return document("accounts/update",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("The account's id"),
                fieldWithPath("oldPassword").type(JsonFieldType.STRING)
                    .description("The account's previous password")
                    .attributes(getPasswordFormat()),
                fieldWithPath("newPassword").type(JsonFieldType.STRING)
                    .description("The account's new password")
                    .attributes(getPasswordFormat())
            )
        );
    }

    public static RestDocumentationResultHandler delete() {
        return document("accounts/delete",
            getDocumentRequest(),
            getDocumentResponse()
        );
    }
}
