# Babelarity
## Cos'è BabelNet
[BabelNet](http://babelnet.org) è una risorsa linguistica multilingua che aggrega informazioni lessicografiche ed enciclopediche provenienti da risorse eterogenee in diverse lingue in un’unica rete semantica. Le maggiori fonti di informazione della rete provengono dalla rete semantica WordNet (https://wordnet.princeton.edu/) e da Wikipedia (https://wikipedia.org).
BabelNet contiene informazioni riguardo concetti ed entità nominali per nomi, aggettivi, avverbi e verbi ed è costruita intorno al concetto di synset (synonym set o insieme di sinonimi). Un synset è un insieme di parole con la stessa parte del discorso (part-of-speech o POS) che possono essere usate e sostituite in un certo contesto. Per esempio le parole { car, auto, automobile, machine, motorcar } formano un synset perché possono essere usate per esprimere lo stesso concetto. Un synset è spesso accompagnato da una descrizione (glossa): "4-wheeled; usually propelled by an internal combustion engine". Inoltre i synset possono essere legati tra loro attraverso relazioni semantiche come iperonimia (tra concetti specifici e più generali), meronimia (tra la parte e il tutto), causa ecc..

## Il Progetto
miniBabelNet rappresenta una versione ridotta e semplificata della rete semantica BabelNet limitata anche alla sola lingua inglese i cui nodi (synset) sono un sottoinsieme di quelli presenti in WordNet e le relazioni tra questi sono arricchite con archi provenienti da altre risorse presenti in BabelNet.
Il progetto consiste nello sviluppo di:
* Un sistema di caricamento e memorizzazione dei documenti di testo
* Una misura di similarità lessicale tra parole nella rete miniBabelNet
* Una misura di similarità semantica tra concetti (synset) nella rete miniBabelNet
* L’implementazione di una misura di similarità tra documenti

## Note
Questo progetto è stato svolto per il corso di Metodologie di Programmazione tenuto dal [prof. Roberto Navigli](https://www.diag.uniroma1.it/navigli/)
