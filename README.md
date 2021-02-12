# ISA
A project developed as part of Internet Software Architecture - an Applied Computer Science subject on Faculty of Technical Sciences.



[Sonarcloud](https://sonarcloud.io/organizations/one-e2-team/projects)

[Heroku backend - ne radi, pretpostavka je da je to zato sto heroku ne podrzava java15](http://isa-eu-backend.herokuapp.com/)

[Heroku frontend](http://isa-frontend.herokuapp.com/#/)

[Task 5.4 pdf](pdf/isa%20%20task%205.4.pdf)

Instrukcije za pokretanje:

# Frontend:
potrebno je imati ```npm``` i pozicionirati se u folder ```isafrontend/frontend```; zatim instalirati node module sa ```npm install``` i pokrenuti server na portu 8080 ```npm run serve```

moguce je i buildovati frontend sa ```npm run build```, u tom slucaju, staticki, minifikovani fajlovi ce se nalaziti u folderu ```/dist```

ako je potrebno promijeniti port servera, to se moze uratiti u ```package.json``` fajlu u build/serve skripti

# Backend
root projekta se nalazi u ```/isabackend```

Potrebne environment varijable:
- nesto=bla
- nestodrugo

# Baza
najlaksa opcija jeste 

```docker run --name db -d -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=test -p 3306:3306 -p 33060:33060 mysql```

# Workbench
Iz raznoraznih poluneobjasnjivih razloga, ne radi spring hibernate automatsko popunjavanje baze preko skripta scheme.sql i data.sql, ybog toga, prepustamo hibernate-u da nakon 126 errora :) sam kreira semu baze (ako se ta ista sema export-uje i da springu - ne radi - sto je veoma fascinantno :)))) )
zbog toga je potrebno koristiti neki ysql client za popunjavanje baye inicijalnim podacima, preporucujemo [MySQL Workbench 8.0 CE](https://www.mysql.com/products/workbench/)
