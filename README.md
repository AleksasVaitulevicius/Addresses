
# Adresu registras
## v.1
## Adresai
Paprastas adresu sarasas. Kiekvienam adresui suteikiamas ID, pagal kuri adresus galima istrinti, surasti.
Galimos komandos:
* GET /address - grazina visus adresus
* GET /address/1 - grazina adresa pagal nurodyta ID(siuo atveju ID=1)
* POST /address - prideda nauja modeli
* PUT /address/1 - prideda arba pakeicia jau esama modeli su nurodytu id(siuo atveju ID=1)
* PATCH /address/1 - pataiso laukus modelio, kurio ID yra nurodytas(siuo atveju ID=1)
* DELETE /address/1 - istrina adresa pagal nurodyta ID(siuo atveju ID=1).
Lenteles atributai: ID, Salis, Miestas, Namo numeris buto numeris, Zip kodas
## Gyventojai
Kiekvienam gyventojui suteikiamas ID pagal kuriuos galima gyventojus surasti istrinti.
Galimos komanods:
* GET /resident - grazina visus gyventojus
* GET /resident/1 - grazina gyventoja pagal nurodyta ID(siuo atveju ID=1)
* POST /resident - prideda nauja modeli
* PUT /resident/1 - prideda arba pakeicia jau esama modeli su nurodytu id(siuo atveju ID=1)
* PATCH /resident/1 - pataiso laukus modelio, kurio ID yra nurodytas(siuo atveju ID=1)
* DELETE /resident/1 - istrina adresa pagal nurodyta ID(siuo atveju ID=1).
Lenteles atributai: ID, vardas, pavarde, asmens kodas, gyvenamojo namo adreso ID

paleidimui naudoti komandas:

`docker build -t laju2259:addresses .`

`docker run -d -p 80:1234 laju2259:addresses`
  
servisu adresai:

`/address`

`/resident`
