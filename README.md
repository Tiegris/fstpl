# fstpl

A doc mappában található a specifikáció és a supports.md fájl, ami azt írja le, hogy milyen funkciói vannak az alkalmazásnak, emellett a template funkciók szintaxisát is leírja.

A doc/tpl_demo mappa egy példa amin meg lehet nézni az alkalmazás minden funkcióját. Mellette található az example.json, ami a tpl_demo template feloldásához használt modell.

A tpl_demo egy springboot alkalmazás inicializálására mutat koncepcionális példát, nem generál ki egy teljes springboot projektet, csak megmutatja, hogy ilyet tud.

A programot a következő parancssori argumentumokkal kell futtatni, hogy az example.json alapján feloldja a tpl_demo tamplatet:

```sh
-v -m ./doc/example.json ./doc/tpl_demo
```

> Ha nem működne, akkor próbáld meg teljes elérési utakkal.

A kimenet a doc/output mappában kell, hogy legyen, de a -o kapcsolóval ez is beállítható.

A -h kapcsolóval meg lehet tekinteni a helpet.
