package features;

import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import io.github.gfrmoretti.functionmap.LongToStringFunctionMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.github.gfrmoretti.AnMap.map;
import static io.github.gfrmoretti.enummap.EnumMapType.MAP_ENUM;
import static io.github.gfrmoretti.enummap.EnumMapType.MAP_ORDINAL;
import static io.github.gfrmoretti.enummap.EnumMapType.MAP_VALUE;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnumMapperTest {
    private final Document document =
            new Document("my id", Status.SUCCESS, Feedback.BE_BETTER, Order.SECOND, Order.FIRST, ErrorStatus.ST_200, ErrorStatus.ST_200, ErrorStatus.ST_200, City.CITY_1, City.CITY_2, Status.ERROR);
    private final DocumentDto documentDto =
            new DocumentDto("2213", "ERROR", "Everything is all right.", "2", 1, "400", ErrorStatusTarget.BAD_REQUEST, ErrorStatusTargetInteger.BAD_REQUEST, "3", "city_1", Result.SUCCESS);

    private static boolean documentIsMappedToDto(Document document, DocumentDto documentDto) {
        return document.uuid.equals(documentDto.uuid) &&
                document.status.toString().equals(documentDto.statusStr) &&
                document.feedback.getValue().equals(documentDto.feedback) &&
                document.orderOrdinal.ordinal() == documentDto.orderInt &&
                document.errorStatus.getValue().toString().equals(documentDto.errorStatus) &&
                document.errorStatusDuplicate.getValue().equals(Integer.parseInt(documentDto.errorStatusTarget.getValue())) &&
                document.errorStatusDuplicate.getValue().equals(documentDto.errorStatusTargetInteger.getValue()) &&
                String.valueOf(document.order.ordinal()).equals(documentDto.order) &&
                document.statusEnum.toString().equals(documentDto.result.toString());
    }

    private static boolean dtoIsMappedToDocument(DocumentDto documentDto, Document document) {
        return document.uuid.equals(documentDto.uuid) &&
                document.status.toString().equals(documentDto.statusStr) &&
                document.feedback.getValue().equals(documentDto.feedback) &&
                document.orderOrdinal.ordinal() == documentDto.orderInt &&
                document.errorStatus.getValue().toString().equals(documentDto.errorStatus) &&
                document.errorStatusDuplicate.getValue().equals(Integer.parseInt(documentDto.errorStatusTarget.getValue())) &&
                document.errorStatusDuplicate.getValue().equals(documentDto.errorStatusTargetInteger.getValue()) &&
                document.statusEnum.toString().equals(documentDto.result.toString()) &&
                String.valueOf(document.order.ordinal()).equals(documentDto.order);
    }

    @Test
    @DisplayName("Should Map Document to DTO.")
    public void shouldMapDocumentToDto() {
        var dto = map(document, DocumentDto.class)
                .orElse(new DocumentDto());
        System.out.println(dto);
        assertTrue(documentIsMappedToDto(document, dto));
    }


    @Test
    @DisplayName("Should Map DTO to Document.")
    public void shouldMapDtoToDocument() {
        var result = map(documentDto, Document.class)
                .orElse(new Document());
        System.out.println(result);
        assertTrue(dtoIsMappedToDocument(documentDto, result));
    }


    @Test
    @DisplayName("Should Not Map Document to DTO.")
    public void shouldNotMapDocumentToDto() {
        var dto = map(document, null);
        assertTrue(dto.isEmpty());
    }


    @Test
    @DisplayName("Should Not Map DTO to Document.")
    public void shouldNotMapDtoToDocument() {
        var result = map(documentDto, null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should Not Map WithNullSource Document to DTO.")
    public void shouldNotMapDocumentToDtoWithNullSource() {
        var dto = map(null, Document.class);
        assertTrue(dto.isEmpty());
    }

    public enum Result {
        ERROR,
        SUCCESS
    }

    public enum Status {
        SUCCESS,
        ERROR
    }

    public enum Order {
        FIRST,
        SECOND,
        THIRD
    }

    @AllArgsConstructor
    @Getter
    public enum Feedback {
        ALL_RIGHT("Everything is all right."),
        BE_BETTER("You need to be better");

        private final String value;
    }

    @AllArgsConstructor
    @Getter
    public enum ErrorStatus {
        ST_200(200),
        ST_202(202),
        ST_400(400),
        ST_500(500);

        private final Integer value;
    }

    @AllArgsConstructor
    @Getter
    public enum City {
        CITY_1(1, "city_1"),
        CITY_2(2, "city_2"),
        CITY_3(3, "city_3");

        private final long id;
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum ErrorStatusTarget {
        OK("200"),
        ACCEPT("202"),
        BAD_REQUEST("400"),
        INTERNAL_SERVER_ERROR("500");

        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum ErrorStatusTargetInteger {
        OK(200),
        ACCEPT(202),
        BAD_REQUEST(400),
        INTERNAL_SERVER_ERROR(500);

        private final int value;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private static class Document {
        private String uuid;
        @EnumMap
        @Map("statusStr")
        private Status status;
        @EnumMap(enumMapType = MAP_VALUE)
        private Feedback feedback;
        @EnumMap(enumMapType = MAP_ORDINAL)
        private Order order;
        @Map("orderInt")
        @EnumMap(enumMapType = MAP_ORDINAL, mustMapOrdinalToInt = true)
        private Order orderOrdinal;
        @EnumMap(enumMapType = MAP_VALUE, functionMapper = IntegerToStringFunctionMapper.class)
        private ErrorStatus errorStatus;
        @Map("errorStatusTargetInteger")
        @EnumMap(enumMapType = MAP_ENUM, matchEnumMapByEnumValue = true)
        private ErrorStatus errorStatusDuplicateInteger;
        @Map("errorStatusTarget")
        @EnumMap(enumMapType = MAP_ENUM, matchEnumMapByEnumValue = true, functionMapper = IntegerToStringFunctionMapper.class)
        private ErrorStatus errorStatusDuplicate;
        @Map("cityId")
        @EnumMap(enumMapType = MAP_VALUE, functionMapper = LongToStringFunctionMapper.class, functionName = "getId")
        private City city;
        @Map("cityName")
        @EnumMap(enumMapType = MAP_VALUE, functionName = "getName")
        private City cityByName;

        @EnumMap(enumMapType = EnumMapType.MAP_ENUM)
        @Map("result")
        private Status statusEnum;
    }

    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DocumentDto {
        private String uuid;
        private String statusStr;
        private String feedback;
        private String order;
        private int orderInt;
        private String errorStatus;
        private ErrorStatusTarget errorStatusTarget;
        private ErrorStatusTargetInteger errorStatusTargetInteger;

        private String cityId;
        private String cityName;
        private Result result;
    }

}
