# 🍔 Food Delivery Platform - Proiect Java (Etapa 1)

## 📋 Actiunile Implementate (Cerinta 1.1)

Sistemul permite executarea a 12 actiuni distincte, grupate pe categorii de business:

### 👤 Gestiunea Utilizatorilor (`UserService`)
1. **Inregistrare Client:** Crearea si validarea unui cont nou de tip `Customer` (cu adresa si status premium).
2. **Inregistrare Sofer:** Crearea unui cont de tip `Driver` (asociat cu un numar de inmatriculare si stare de disponibilitate).
3. **Afisarea Soferilor:** Filtrarea colectiei de utilizatori si extragerea tuturor soferilor din sistem.
4. **Afisarea Clientilor:** Filtrarea si afisarea tuturor clientilor inregistrati.

### 🍕 Gestiunea Restaurantelor (`RestaurantService`)
5. **Adaugare Restaurant:** Extinderea platformei prin adaugarea de noi localuri.
6. **Gestionare Meniu:** Adaugarea de produse noi (`MenuItem`) in meniul specific unui restaurant.
7. **Cautare Localuri:** Functionalitate de filtrare a restaurantelor disponibile in functie de oras.
8. **Sortare Meniu:** Afisarea produselor dintr-un restaurant sortate crescator dupa pret (folosind `Collections.sort`).

### 📦 Fluxul de Comenzi (`OrderService`)
9. **Pregatire Comanda:** Adaugarea produselor in cosul clientului (`addToCart`), cu validare stricta pentru a preveni combinarea produselor de la restaurante diferite.
10. **Plasare Comanda:** Preluarea cosului, generarea facturii prin `PaymentService`, asignarea automata a celui mai apropiat sofer disponibil si curatarea cosului.
11. **Actualizare Status:** Actiune declansata de livrator pentru a marca finalizarea comenzii (`deliverOrder`), actiune ce elibereaza soferul pentru comenzi viitoare.
12. **Istoric Comenzi:** Salvarea in `HashMap` si afisarea tuturor tranzactiilor anterioare efectuate de un client.

---

## 🏗️ Modele de Date (Entitati / Clase)
Sistemul este construit pe baza a 10 clase si enumerari principale:
1. `User` (Abstract) - Baza pentru toti utilizatorii platformei.
2. `Customer` - Mosteneste User; detine un cos de cumparaturi si o adresa.
3. `Driver` - Mosteneste User; gestioneaza comenzile curente si disponibilitatea auto.
4. `Address` - Clasa ajutatoare pentru localizare.
5. `Restaurant` - Detine un meniu, o locatie si un status (Open/Closed).
6. `MenuItem` - Produs individual cu pret si referinta la restaurantul mama.
7. `Order` - Agregarea informatiilor (Client, Produse, Total, Sofer, Status).
8. `TransactionRecord` - Entitate imuabila generata de serviciul de plati pentru chitante.
9. `OrderStatus` (Enum) - Defineste starile unei comenzi (PLACED, DELIVERED etc.).
10. `PaymentMethod` (Enum) - Optiunile de plata disponibile (CARD, CASH).

## 🚀 Cum se ruleaza
Toate actiunile de mai sus sunt demonstrate sub forma unui flux logic in interiorul clasei `Main.java`.