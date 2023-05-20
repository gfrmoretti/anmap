package quickstart;

import io.github.gfrmoretti.AnMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import quickstart.domains.bestof.Door;
import quickstart.domains.bestof.House;
import quickstart.domains.bestof.ValuationStatus;
import quickstart.domains.constructormap.Transaction;
import quickstart.domains.datemap.Person;
import quickstart.domains.enummap.*;
import quickstart.domains.functionmap.Document;
import quickstart.domains.functionmap.Email;
import quickstart.domains.implicitmap.Board;
import quickstart.domains.implicitmap.Game;
import quickstart.domains.implicitmap.Player;
import quickstart.domains.implicitmap.Point;
import quickstart.domains.simplemap.Address;
import quickstart.domains.simplemap.Status;
import quickstart.domains.simplemap.User;
import quickstart.requests.bestof.HouseRequest;
import quickstart.requests.constructormap.TransactionRequest;
import quickstart.requests.datemap.PersonRequest;
import quickstart.requests.enummap.PaymentRequest;
import quickstart.requests.functionmap.DocumentRequest;
import quickstart.requests.implicitmap.GameRequest;
import quickstart.requests.simplemap.InsertUserRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuickStartTest {

    @Test
    @DisplayName("Quick Start: Simple map.")
    void simpleMap() {
        var user = new User("gm123456", "test", 15, Status.ENABLE,
                new Address("Street 14", 444, "Pan City")
        );

        var request = AnMap.map(user, InsertUserRequest.class).orElse(null);

        assertNotNull(request);
        System.out.println(request);
    }

    @Test
    @DisplayName("Quick Start: Functions map.")
    void functionMap() {
        var doc = new Document("1", List.of("123", "456"), Set.of("123", "456"), new Email("gm@gm.com"));

        var request = AnMap.map(doc, DocumentRequest.class).orElse(null);

        assertNotNull(request);
        System.out.println(request);
    }


    @Test
    @DisplayName("Quick Start: Enum map.")
    void enumMap() {
        var payment = new Payment("1", PaymentCoin.REAL, StatusAccounting.PENDING,
                PaymentType.SINGLE, StatusTransfer.ERROR, PaymentValueType.PV_ZERO, ReturnStatus.OK);

        var request = AnMap.map(payment, PaymentRequest.class).orElse(null);

        assertNotNull(request);
        System.out.println(request);
    }


    @Test
    @DisplayName("Quick Start: Date map.")
    void dateMap() {
        var person = new Person("gm", Instant.ofEpochSecond(853008158), "22/04/1969", Instant.now());

        var request = AnMap.map(person, PersonRequest.class).orElse(null);

        assertNotNull(request);
        System.out.println(request);
    }


    @Test
    @DisplayName("Quick Start: Implicit map.")
    void implicitMap() {
        var player1 = new Player("p1", new Point(12, 16), Instant.now());
        var player2 = new Player("p2", new Point(20, 100), Instant.now());
        var game = new Game(new Board(62434, "My initial board"), List.of(player1, player2));

        var request = AnMap.map(game, GameRequest.class).orElse(null);

        assertNotNull(request);
        System.out.println(request);
    }

    @Test
    @DisplayName("Quick Start: Constructor map.")
    void constructorMap() {
        var transaction = new Transaction("123", "payment", null, new BigDecimal(20));

        var result = AnMap.mapOrElseThrow(transaction, TransactionRequest.class);

        assertEquals("priority 0", result.getPriority());
        System.out.println(result);
    }

    @Test
    @DisplayName("Quick Start: Best of AnMap")
    void bestOf() {
        var doors = List.of(new Door(120, 130), new Door(120, 90));
        var house = new House(123, "owner", doors, ValuationStatus.HIGH, Instant.now(), new BigDecimal(10000));

        var request = AnMap.map(house, HouseRequest.class).orElse(null);

        assertNotNull(request);
        System.out.println(request);
    }
}
