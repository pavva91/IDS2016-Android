# IDS2016-Android
##COMANDI GIT INIZIALI
- creazione branch nel proprio pc: **git checkout [NOME_BRANCH]** ; consiglio di creare ognuno il proprio branch con il suo nome, ES: git checkout valerio
- pubblicazione branch su github: **git push origin [NOME_BRANCH]**
- eliminazione del branch pubblico: **git push origin : [NOME_BRANCH]**
- settare push branch remoto su branch pubblico : **git push --set-upstream [NOME_BRANCH]** ; una volta "settati" i due branch remoto-pubblico ogni **git push** dal branch remoto va a fare la push sul branch pubblico


N.B. Ricordarsi di lavorare sul proprio branch in locale e non sul master, in modo che le push vadano in automatico sui branch pubblici.
I merge/push sul master li facciamo una volta che le modifiche fatte sul branch sono stabili in modo da avere sempre sul master l'ultima copia stabile del progetto.

##CODING STANDARD
- [Google Java Style standard] (https://google.github.io/styleguide/javaguide.html).