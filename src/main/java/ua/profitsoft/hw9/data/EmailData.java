package ua.profitsoft.hw9.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(indexName = "emails")
public class EmailData {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private List<String> to;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String body;

    @Field(type = FieldType.Keyword)
    private EmailStatus status;

    @Field(type = FieldType.Text)
    private List<String> transactionIds;

    @Field(type = FieldType.Text)
    private String errorMessage;

    public void addTransactionId(String transactionId) {
        if (this.transactionIds == null)
            this.transactionIds = new ArrayList<>(2);
        this.transactionIds.add(transactionId);
    }
}
