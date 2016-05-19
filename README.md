# IDS2016-Android
Development Android App for IDS project 2016
##COMANDI GIT INIZIALI
- creazione branch nel proprio pc (locale): **git checkout [NOME_BRANCH]** ; consiglio di creare ognuno il proprio branch con il suo nome, ES: git checkout valerio
- pubblicazione branch su github: **git push origin [NOME_BRANCH]**
- eliminazione del branch pubblico: **git push origin : [NOME_BRANCH]**
- settare push branch locale su branch pubblico : **git push --set-upstream [NOME_BRANCH]** ; una volta "settati" i due branch locale-pubblico ogni **git push** dal branch locale va a fare la push sul branch pubblico
- **MERGE PARZIALE**: aggiungere solo dei file specifici **[PATH]** dal proprio branch **[NOME_BRANCH]** al branch master: 2 COMANDI: **git checkout master** ------>>>>> **git checkout [NOME_BRANCH] [PATH]**

N.B. Ricordarsi di lavorare sul proprio branch in locale e non sul master, in modo che le push vadano in automatico sui branch pubblici.
I merge/push sul master li facciamo una volta che le modifiche fatte sul branch sono stabili in modo da avere sempre sul master l'ultima copia stabile del progetto.

##CODING STANDARD
- [Google Java Style standard] (https://google.github.io/styleguide/javaguide.html).

##IMPLEMENTAZIONE MVP
- Applicazione di esempio per uso MVP pattern: [Applicazione esempio MVP] (https://github.com/sockeqwe/mosby).

##LIBRARY MVP UTILIZZATA
- [Mosby] (http://hannesdorfmann.com/mosby/).

##BUTTERKNIFE
- Spiegazione [ButterKnife] (http://jakewharton.github.io/butterknife/).

##DB-FLOW
- A robust, powerful, and very simple ORM android database library with annotation processing. Let [DBFlow] (https://github.com/Raizlabs/DBFlow) make SQL code flow like a steady stream so you can focus on writing amazing apps.