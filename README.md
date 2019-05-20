# sabaodycz-linkparser
https://github.com/kukosek/sabaodycz-linkparser/raw/master/build/SabaodyCZlinkParser.jar

Java program na protřídění linků na stahování anime "one piece" ze stránky [sabaody.wz.cz](http://sabaody.wz.cz/anime.php). Vytvořil jsem ho, protože jsem si chtěl stáhnout všechny díly naráz přes linky na mega.nz z této stránky, problémem ale bylo, že ke každému dílu máte několik možností. Tímto jsem si celkem ulehčil práci. Na následné hromadné stáhnutí linků z mega.nz doporučuji [megabasterd](https://github.com/tonikelope/megabasterd)

Program používá knihovnu [JSoup](https://github.com/jhy/jsoup) a kod je dost špatný, ale nějak to funguje. Určitě tam jsou nějaké chyby, tak kdyžtak reportujte do issues.

![Ukázka programu](https://raw.githubusercontent.com/kukosek/sabaodycz-linkparser/master/obrazek.png "Ukázka z konzole")

## návod
1. Nainstalujte si javu, pokud ji již nemáte
2. Stáhněte si [soubor .jar](https://github.com/kukosek/sabaodycz-linkparser/raw/master/build/SabaodyCZlinkParser.jar)
3. Otevřete příkazový řádek (cmd nebo terminal) a nastavte umístnění do kterého jste uložili soubor .jar. Příklad:
```
cd C:\Users\uzivatel\Downloads
```
4. Spusťte program příkazem
```
java -jar SabaodyCZlinkParser.jar
```

Pokud dostáváte chybu "..not marked as executable", vyřešíte to jednoduše (na linuxu):
```
cd C:\Users\uzivatel\Downloads
chmod +x SabaodyCZlinkParser.jar
```

5. hotovo. pište to, co chcete, ale pozor, pokud např. napíšete všude "vsechny", tak to neznamená, že se od každého dílu vypíšou všechny vyhovující odkazy. Program vyhodí ke každému dílu vždy jen jeden odkaz.
