package features;

import io.github.gfrmoretti.annotations.DateMap;
import features.models.TransferDto;
import features.models.TransferLocalTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.github.gfrmoretti.AnMap.map;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateValueMapperTest {

    private static final LocalDateTime fromDomainDateDefault =
            LocalDateTime.of(2022, 2, 22, 2, 20, 43);
    private final TransferLocalTime transfer =
            new TransferLocalTime("my id", 20.0, 300, fromDomainDateDefault, "Test");
    private final TransferDto transferDto =
            new TransferDto("transfer dto id", 50.0, 1000, "02/12/2020 14:23:45");

    private static boolean transferIsMappedToTransferDto(TransferLocalTime transfer, TransferDto transferDto) {
        return transfer.uuid.equals(transferDto.id) &&
                transfer.number == transferDto.number &&
                "22/02/2022 02:20:43".equals(transferDto.date) &&
                transferDto.size == null;
    }

    private static boolean transferDtoIsMappedToTransfer(TransferDto transferDto, TransferLocalTime transfer) {
        return transfer.uuid.equals(transferDto.id) &&
                transfer.number == transferDto.number &&
                "02/12/2020 14:23:45".equals(transferDto.date) &&
                transfer.name == null &&
                transferDto.size == 1000;
    }

    @Test
    @DisplayName("Should map TransferLocalTime object to transfer dto by class reference.")
    public void shouldMapTransferToTransferDtoByClassReference() {

        var dto = map(transfer, TransferDto.class).orElseThrow();

        assertTrue(transferIsMappedToTransferDto(transfer, dto));
    }

    @Test
    @DisplayName("Should map transferDto object to TransferLocalTime by class reference.")
    public void shouldMapTransferDtoToTransferByClassReference() {

        var transferObj = map(transferDto, TransferLocalTime.class).orElseThrow();

        assertTrue(transferDtoIsMappedToTransfer(transferDto, transferObj));
    }

    @Test
    @DisplayName("Should NOT map transfer object to TransferDto wrong format pattern by class reference.")
    public void shouldNotMapTransferToTransferDtoWithWrongFormatPatternByClassReference() {
        //Given
        var transfer = new WrongTransfer(LocalDate.now());

        var result = map(transfer, WrongTransferDto.class).orElseThrow();

        assertNull(result.date);
    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    private static class WrongTransfer {
        private LocalDate date;
    }

    @NoArgsConstructor
    @ToString
    private static class WrongTransferDto {
        @DateMap(formatPattern = "wrong format")
        public String date;
    }
}
