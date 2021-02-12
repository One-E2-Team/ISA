# ISA
A project developed as part of Internet Software Architecture - an Applied Computer Science subject on Faculty of Technical Sciences.



[Sonarcloud](https://sonarcloud.io/organizations/one-e2-team/projects)

[Heroku backend - ne radi, pretpostavka je da je to zato što heroku ne podržava java15](http://isa-eu-backend.herokuapp.com/)

[Heroku frontend](http://isa-frontend.herokuapp.com/#/)

[Task 5.4 pdf](pdf/isa%20%20task%205.4.pdf)

Instrukcije za pokretanje:

## Frontend:
potrebno je imati ```npm``` i pozicionirati se u folder ```isafrontend/frontend```; zatim instalirati node module sa ```npm install``` i pokrenuti server na portu 8080 ```npm run serve```

moguće je i build-ovati frontend sa ```npm run build```, u tom slučaju, statički, minifikovani fajlovi će se nalaziti u folderu ```/dist```

ako je potrebno promijeniti port servera, to se moze uratiti u ```package.json``` fajlu u build/serve skripti

## Backend

koristi se java ```jdk 15```

root projekta se nalazi u ```/isabackend```

Potrebne environment varijable:
- DB_ISA_PASSWORD=password
- DB_ISA_PORT=3306
- DB_ISA_SCHEMA=test
- DB_ISA_SERVER=localhost
- DB_ISA_USERNAME=root
- ISA_MAIL_PASSWORD=oNeE2tEaM
- ISA_MAIL_USERNAME=Isaonee2team@gmail.com
- ISA_SPRING_HOST=localhost
- ISA_SPRING_PORT=8083
- nestodrugo

## Baza
najlakša opcija jeste 

```docker run --name db -d -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=test -p 3306:3306 -p 33060:33060 mysql```

## Workbench
Iz raznoraznih, poluneobjašnjivih razloga, ne radi spring hibernate automatsko popunjavanje baze preko skripta scheme.sql i data.sql, zbog toga, prepuštamo hibernate-u da nakon 126 errora :) sam kreira šemu baze (ako se ta ista šema export-uje i da springu - ne radi - što je veoma fascinantno :)))) )
zbog toga je potrebno koristiti neki mysql client za popunjavanje baze inicijalnim podacima, preporučujemo [MySQL Workbench 8.0 CE](https://www.mysql.com/products/workbench/)
