package features;

import io.github.gfrmoretti.datemap.StringToTemporalMapper;
import features.models.Transfer;
import features.models.TransferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static io.github.gfrmoretti.AnMap.mapSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapperSetTest {

    private Set<Transfer> transfers;
    private int sizeExpected;

    private static boolean transferIsMappedToTransferDto(Transfer transfer, TransferDto transferDto) {
        return transfer.uuid.equals(transferDto.id) &&
                transfer.number == transferDto.number &&
                new StringToTemporalMapper(Instant.class, "yyyy-MM-dd").mapToString(transfer.date).orElse("")
                        .equals(transferDto.date) &&
                transferDto.size == null;
    }

    @BeforeEach
    public void setup() {
        var transfer = new Transfer("1", 2, 300, Instant.now(), "name1");
        var transfer2 = new Transfer("2", 2, 1300, Instant.now(), "name2");
        var transfer3 = new Transfer("3", 3, 3300, Instant.now(), "name3");
        transfers = Set.of(transfer, transfer2, transfer3);
        sizeExpected = transfers.size();
    }

    @Test
    @DisplayName("Should match size map list successfully.")
    public void shouldMapAllElementsOnList() {

        var result = mapSet(transfers, TransferDto.class);

        assertEquals(sizeExpected, result.size());
    }

    @Test
    @DisplayName("Should map list successfully and all elements are not null.")
    public void shouldMapListNonNulls() {

        var result = mapSet(transfers, TransferDto.class);

        assertTrue(result.stream().noneMatch(Objects::isNull));
    }

    @Test
    @DisplayName("Should map list successfully and all fields must match.")
    public void shouldMapListMatchAllFields() {

        var result = mapSet(transfers, TransferDto.class);

        assertTrue(result.stream().anyMatch(transferDto ->
                transfers.stream().anyMatch(
                        transfer -> transferIsMappedToTransferDto(transfer, transferDto)
                )
        ));
    }

    @Test
    @DisplayName("Should map an empty list because null source.")
    public void shouldMapAnEmptyListWithNullSource() {

        var result = mapSet(null, TransferDto.class);

        assertTrue(result.isEmpty());
    }


    @Test
    @DisplayName("Should map an empty list because null target class.")
    public void shouldMapAnEmptyListWithNullTargetClass() {

        var result = mapSet(transfers, null);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should map only non null objects on list.")
    public void shouldMapOnlyNonNullObjects() {

        var listWithNulls = new HashSet<>(transfers);
        listWithNulls.addAll(Arrays.asList(null, null, null));


        var result = mapSet(listWithNulls, TransferDto.class);

        assertEquals(sizeExpected, result.size());
    }
}
