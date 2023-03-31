package features;

import io.github.gfrmoretti.datemap.StringToTemporalMapper;
import features.models.Transfer;
import features.models.TransferDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static io.github.gfrmoretti.AnMap.map;
import static io.github.gfrmoretti.AnMap.mapOrElseThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapperTest {

    private static final Instant fromDomainDateDefault =
            LocalDateTime.of(2022, 2, 22, 0, 0, 0)
                    .toInstant(ZoneOffset.UTC);

    private final Instant toDomainDateDefault =
            LocalDateTime.of(2022, 4, 4, 0, 0, 0)
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
                transferDto.size == null;
    }

    private static boolean transferDtoIsMappedToTransfer(TransferDto transferDto, Transfer transfer) {
        return transfer.uuid.equals(transferDto.id) &&
                transfer.number == transferDto.number &&
                new StringToTemporalMapper(Instant.class, "yyyy-MM-dd").mapToString(transfer.date).orElse("")
                        .equals(transferDto.date) &&
                transfer.name == null &&
                transferDto.size == 1000;
    }

    @Test
    @DisplayName("Should map transfer object to transfer dto by class reference.")
    public void shouldMapTransferToTransferDtoByClassReference() {

        var dto = map(transfer, TransferDto.class).orElseThrow();

        assertTrue(transferIsMappedToTransferDto(transfer, dto));
    }


    @Test
    @DisplayName("Should map transferDto object to transfer by class reference.")
    public void shouldMapTransferDtoToTransferByClassReference() {

        var transferObj = map(transferDto, Transfer.class).orElseThrow();

        assertTrue(transferDtoIsMappedToTransfer(transferDto, transferObj));
    }

    @Test
    @DisplayName("Should mapOrElseThrow transfer object to transfer dto by class reference.")
    public void shouldMapOrElseThrowTransferToTransferDtoByClassReference() {

        var dto = mapOrElseThrow(transfer, TransferDto.class);

        assertTrue(transferIsMappedToTransferDto(transfer, dto));
    }


    @Test
    @DisplayName("Should mapOrElseThrow transferDto object to transfer by class reference.")
    public void shouldMapOrElseThrowTransferDtoToTransferByClassReference() {

        var transferObj = mapOrElseThrow(transferDto, Transfer.class);

        assertTrue(transferDtoIsMappedToTransfer(transferDto, transferObj));
    }

    @Test
    @DisplayName("Should throw when use mapOrElseThrow transfer object to transfer dto by class reference.")
    public void shouldThrowMapOrElseThrowTransferToTransferDtoByClassReference() {

        assertThrows(Exception.class, () -> mapOrElseThrow(null, TransferDto.class));
    }


    @Test
    @DisplayName("Should throw when use mapOrElseThrow transferDto object to transfer by class reference.")
    public void shouldThrowMapOrElseThrowTransferDtoToTransferByClassReference() {

        assertThrows(Exception.class, () -> mapOrElseThrow(null, Transfer.class));
    }

    @Test
    @DisplayName("Should not map id of transferDto to transfer by class reference.")
    public void shouldNotMapUuidDomainToTransferObject() {
        //Given
        transferDto.id = null;

        var result = map(transferDto, Transfer.class).orElseThrow();

        assertNull(result.uuid);
    }

    @Test
    @DisplayName("Should NOT map number field on transfer object to numner field on domain by object reference.")
    public void shouldNotMapNumberTransferObjectToDomain() {
        //Given
        transferDto.number = null;

        var result = map(transferDto, Transfer.class).orElseThrow();

        assertNotEquals(result.number, transferDto.number);
    }

    @Test
    @DisplayName("Should NOT map date String field on transfer object to date Instant field on domain by object reference.")
    public void shouldMapNotDateTransferObjectToDomain() {
        //Given
        transferDto.date = null;

        var result = map(transferDto, Transfer.class).orElseThrow();

        assertNotEquals(result.date, toDomainDateDefault);
    }

    @Test
    @DisplayName("Should NOT map uuid field on domain to Id field on transfer object by object reference.")
    public void shouldNotMapIdTransferObjectToDomain() {
        //Given
        transfer.uuid = null;

        var dto = map(transfer, TransferDto.class).orElseThrow();

        assertNull(dto.id);
    }

    @Test
    @DisplayName("Should NOT map date Instant field on domain to date String field on transfer object by object reference.")
    public void shouldNotMapDateDomainFromToTransferObject() {
        //Given
        transfer.date = null;

        var dto = map(transfer, TransferDto.class).orElseThrow();

        String dateDefaultStr = "2022-02-22";
        assertNotEquals(dateDefaultStr, dto.date);
    }

    @Test
    @DisplayName("Should NOT map if receive null parameter on first args.")
    public void shouldNotMapWithNullParametersFromDomainFirstArgs() {

        var result = map(null, TransferDto.class);

        assertTrue(result.isEmpty());
    }


    @Test
    @DisplayName("Should NOT map if receive null parameter on second args.")
    public void shouldNotMapWithNullParametersFromDomainSecondArgs() {

        var result = map(transfer, null);

        assertTrue(result.isEmpty());
    }
}
