<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="it">
	<head>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="widht=device-width, initial-scale=1">
		<title>Tekkies</title>
		<link rel="stylesheet" href="css/style.css">
		<link rel="stylesheet" href="font-awesome/css/font-awesome.css">
		<script src="/js/script.js"></script>
	</head>
	
	
	<body>
	
	<%@ include file="header.jsp" %>
	
	<section class="home" id="home">

		<div class="slide-container active">
			<div class="slide">
				<div class="content">
					<span>Adidas Originals</span>
					<h3>Adi2000</h3>
					<p>
					UN LOOK RUNNING-INSPIRED CON DETTAGLI INEDITI.
Accendi il tuo lato ribelle con le scarpe adi2000, ispirate all'audacia di inizio millennio. Lo stile ripreso dal mondo dello skate è aggiornato in una versatile palette di colori. Le 3 strisce sono rielaborate con un tocco innovativo mentre la tomaia in pelle e la suola in gomma assicurano massima morbidezza.

Prodotto realizzato utilizzando materiale riciclato ottenuto da scarti di produzione, come i ritagli di stoffa, e da rifiuti domestici per ridurre l'impatto ambientale legato alla produzione di materiali vergini.
					</p>
					
					<a href="#" class="btn">Aggiungi al carrello</a>
				</div>
				<div class="image">
					<img src="imgs/slide/adi2000.png" class="shoe" alt="first shoe">
				</div>
			</div>
		</div>
		
		
		<div class="slide-container">
			<div class="slide">
				<div class="content">
					<span>Adidas Originals</span>
					<h3>Adi2000</h3>
					<p>
					UN LOOK RUNNING-INSPIRED CON DETTAGLI INEDITI.
Accendi il tuo lato ribelle con le scarpe adi2000, ispirate all'audacia di inizio millennio. Lo stile ripreso dal mondo dello skate è aggiornato in una versatile palette di colori. Le 3 strisce sono rielaborate con un tocco innovativo mentre la tomaia in pelle e la suola in gomma assicurano massima morbidezza.

Prodotto realizzato utilizzando materiale riciclato ottenuto da scarti di produzione, come i ritagli di stoffa, e da rifiuti domestici per ridurre l'impatto ambientale legato alla produzione di materiali vergini.
					</p>
					
					<a href="#" class="btn">Aggiungi al carrello</a>
				</div>
				<div class="image">
					<img src="imgs/slide/adi2000white.png" class="shoe" alt="second shoe">
				</div>
			</div>
		</div>
		
		
		<div class="slide-container">
			<div class="slide">
				<div class="content">
					<span>Adidas Originals</span>
					<h3>Adi2000</h3>
					<p>
SNEAKER ICONICHE REALIZZATE IN PARTE CON MATERIALI RICICLATI.
Le adidas adi2000 nascono sotto il segno dello stile old-school, ma ci pensano la struttura leggermente voluminosa e i dettagli innovati a renderla una silhouette contemporanea. Un modello che celebra l'autoespressione e l'individualità, queste sneaker sono caratterizzate dai pannelli multistrato che creano un look dinamico e versatile. Il Trifoglio aggiunge un tocco di carattere.

La tomaia è composta per almeno il 50% da materiali riciclati. Questo prodotto è solo una delle nostre azioni concrete per contrastare l'inquinamento causato dalla plastica.
					</p>
					
					<a href="#" class="btn">Aggiungi al carrello</a>
				</div>
				<div class="image">
					<img src="imgs/slide/adi2000grey.png" class="shoe" alt="third shoe">
				</div>
			</div>
		</div>
		
		<div id="prev" class="fa fa-chevron-left" onclick="prev()"></div>
		<div id="next" class="fa fa-chevron-right" onclick="next()"></div>
	
	</section>
	
	<section class="prodotti" id="prodotti">
		<h1></h1>
	</section> 
	
	<%@ include file="footer.jsp" %>
			
	</body>
</html>