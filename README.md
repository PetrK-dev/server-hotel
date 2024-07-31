!Aplikace není funkční z důvodu odstranění některých částí a slouží pouze jako ukázka!

1)do terminalu napiste "gradle build"

2)do terminalu napiste "gradle bootJar"

3)spustime server

4)Requesty:

    Client:
        zobrazit vsechny klienty:   GET http://localhost:8080/client/all
        zobrazit klienta s ID:      GET http://localhost:8080/client/{id}
        vytvorit klienta:           POST http://localhost:8080/client + {"firstName": "", "lastName": ""}
        aktualizovat klienta:       PUT http://localhost:8080/client/{id} + {"firstName": "", "lastName": ""}
        odstranit klienta s ID:     DLETE http://localhost:8080/client/{id}

    Equipment:
        zobrazit vsechno vybaveni:  GET http://localhost:8080/equipment/all
        zobrazit vybaveni s ID:     GET http://localhost:8080/equipment/{id}
        vytvorit vybaveni:          POST http://localhost:8080/equipment + {"name": ""}
        aktualizovat vybaveni:      PUT http://localhost:8080/equipment/{id} + {"name": ""}
        odstranit vybaveni s ID:    DLETE http://localhost:8080/equipment/{id}

    Reservation:
        zobrazit vsechny rezervace: GET http://localhost:8080/reservation/all
        zobrazit rezervaci s ID:    GET http://localhost:8080/reservation/{id}
        vytvorit rezervaci:         POST http://localhost:8080/reservation + {"from": "YYYY-MM-DD", "to": "YYYY-MM-DD", "reservationOfClientId": , "reservationOfApartmentId": }
        aktualizovat rezervaci:     PUT http://localhost:8080/reservation/{id} + {"from": "YYYY-MM-DD", "to": "YYYY-MM-DD", "reservationOfClientId": , "reservationOfApartmentId": }
        odstranit rezervaci s ID:   DLETE http://localhost:8080/reservation/{id}
    
    Apartment:
        zobrazit vsechny apartmany: GET http://localhost:8080/apartment/all
        zobrazit apartman s ID:     GET http://localhost:8080/apartment/{id}
        zobrazit apart. s disp. bez rez. GET http://localhost:8080/apartment?disposition=Number%2BNumber (replace words Number by numbers)
        zobrazit apart. klienta     GET http://localhost:8080/apartment?client=<id_client>
        vytvorit apartman:          POST http://localhost:8080/apartment + {"price": , "disposition": "2+1", "equipmentsIDs": []}
        aktualizovat apartman:      PUT http://localhost:8080/apartment/{id} + {"price": , "disposition": "3+1", "equipmentsIDs": []}
        odstranit apartman s ID:    DLETE http://localhost:8080/apartment/{id}
