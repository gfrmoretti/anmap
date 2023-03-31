package features;

import io.github.gfrmoretti.custom.CustomMapper;
import io.github.gfrmoretti.datemap.StringToTemporalMapper;
import features.models.Transfer;
import features.models.TransferDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static io.github.gfrmoretti.AnMap.mapCustom;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomMapperTest {
    private static final Instant fromDomainDateDefault =
            LocalDateTime.of(2022, 2, 22, 0, 0, 0)
                    .toInstant(ZoneOffset.UTC);
    private final Transfer transfer =
            new Transfer("my id", 20.0, 300, fromDomainDateDefault, "Test");
    private final TransferDto transferDto =
            new TransferDto("transfer dto id", 50.0, 1000, "2022-04-04");

    private static boolean transferIsMappedToTransferDto(Transfer transfer, TransferDto transferDto) {
        return transfer.uuid.equals(transferDto.id) &&
                transfer.number == transferDto.number &&
                new StringToTemporalMapper(Instant.class, "yyyy-MM-dd").mapToString(transfer.date).orElse("")
                        .equals(transferDto.date) &&
                transferDto.size == transfer.size;
    }

    private static boolean transferDtoIsMappedToTransfer(TransferDto transferDto, Transfer transfer) {
        return transfer.uuid.equals(transferDto.id) &&
                transfer.number == transferDto.number &&
                new StringToTemporalMapper(Instant.class, "yyyy-MM-dd").mapToString(transfer.date).orElse("")
                        .equals(transferDto.date) &&
                transfer.name == null &&
                transferDto.size == transfer.size;
    }

    @Test
    @DisplayName("Should Map Transfer to DTO by custom mapper.")
    public void shouldMapTransferToDtoByCustomMapper() {
        var dto = mapCustom(transfer, new CustomTransferMapper())
                .orElse(new TransferDto());
        assertTrue(transferIsMappedToTransferDto(transfer, dto));
    }


    @Test
    @DisplayName("Should Map DTO to Transfer by custom mapper.")
    public void shouldMapDtoToTransferByCustomMapper() {
        var result = mapCustom(transferDto, new CustomTransferDtoMapper())
                .orElse(new Transfer());
        assertTrue(transferDtoIsMappedToTransfer(transferDto, result));
    }


    @Test
    @DisplayName("Should Not Map Transfer to DTO by custom mapper.")
    public void shouldNotMapTransferToDtoByCustomMapper() {
        var dto = mapCustom(transfer, null);
        assertTrue(dto.isEmpty());
    }


    @Test
    @DisplayName("Should Not Map DTO to Transfer by custom mapper.")
    public void shouldNotMapDtoToTransferByCustomMapper() {
        var result = mapCustom(transferDto, null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should Not Map WithNullSource Transfer to DTO by custom mapper.")
    public void shouldNotMapTransferToDtoByCustomMapperWithNullSource() {
        var dto = mapCustom(null, new CustomTransferMapper());
        assertTrue(dto.isEmpty());
    }


    @Test
    @DisplayName("Should Not Map WithNullSource DTO to Transfer by custom mapper.")
    public void shouldNotMapDtoToTransferByCustomMapperWithNullSource() {
        var result = mapCustom(null, new CustomTransferDtoMapper());
        assertTrue(result.isEmpty());
    }

    private static class CustomTransferMapper implements CustomMapper<Transfer, TransferDto> {

        @Override
        public Optional<TransferDto> map(Transfer transfer) {
            var transferDto = new TransferDto();
            transferDto.id = transfer.uuid;
            transferDto.number = transfer.number;
            transferDto.date = new StringToTemporalMapper(Instant.class, "yyyy-MM-dd").mapToString(transfer.date).orElse(null);
            transferDto.size = transfer.size;
            return Optional.of(transferDto);
        }
    }

    private static class CustomTransferDtoMapper implements CustomMapper<TransferDto, Transfer> {

        @Override
        public Optional<Transfer> map(TransferDto transferDto) {
            var transfer = new Transfer();
            transfer.uuid = transferDto.id;
            transfer.number = transferDto.number;
            transfer.date = (Instant) new StringToTemporalMapper(Instant.class, "yyyy-MM-dd").mapToTemporal(transferDto.date).orElse(null);
            transfer.size = transferDto.size;
            return Optional.of(transfer);
        }
    }
}
