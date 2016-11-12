<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li class="dropdown brand">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
            <span class="tumjudge-instance tumjudge-info"><span class="pagetitle">ICPC@TUM</span> Info</span>
            <span class="tumjudge-instance tumjudge-main"><span class="pagetitle">TUMjudge</span> Main</span>
            <span class="tumjudge-instance tumjudge-test"><span class="pagetitle">TUMjudge</span> Test</span>
            <span class="tumjudge-instance tumjudge-conpra"><span class="pagetitle">TUMjudge</span> ConPra</span>
            <span class="tumjudge-instance tumjudge-contest"><span class="pagetitle">TUMjudge</span> Contest</span>
            <span class="tumjudge-instance tumjudge-gad"><span class="pagetitle">TUMjudge</span> GAD</span>
            <span class="tumjudge-instance tumjudge-isabelle"><span class="pagetitle">TUMjudge</span> Isabelle</span>
            <span class="tumjudge-instance tumjudge-gcpc"><span class="pagetitle">TUMjudge</span> GCPC</span>
            <span class="tumjudge-instance tumjudge-ioide"><span class="pagetitle">TUMjudge</span> IOI Germany</span>
            <span class="tumjudge-instance tumjudge-ioiat"><span class="pagetitle">TUMjudge</span> IOI Austria</span>
            <span class="tumjudge-instance tumjudge-challenge"><span class="pagetitle">TUMjudge</span> Challenge</span>
            <span class="tumjudge-instance tumjudge-mlr"><span class="pagetitle">TUMjudge</span> MLR</span>
            <span class="caret"></span>
          </a>
          <ul class="dropdown-menu" role="menu">
            <li><a class="pagetitle">ICPC@TUM</a></li>
                        <li class="page tumjudge-info"><a href="https://icpc.tum.de">Website <span class="description">Information about the ICPC</span></a></li>
            <li class="page tumjudge-discuss"><a href="https://judge.in.tum.de/discuss/">Discuss <span class="description">Discuss all about TUMjudge and ICPC</span></a></li>
            <li><a class="pagetitle">TUMjudge</a></li>
                        <li class="page tumjudge-conpra"><a href="https://judge.in.tum.de/conpra/">ConPra <span class="description">Lecture &ldquo;Algorithms for Programming Contests&rdquo;</span></a></li>
            <li class="page tumjudge-contest"><a href="https://judge.in.tum.de/contest/">Contest <span class="description">Preparation for the ACM ICPC</span></a></li>
            <li class="page tumjudge-gad"><a href="https://judge.in.tum.de/gad/">GAD <span class="description">Lecture &ldquo;Foundations: Algorithms and Data Structures&rdquo;</span></a></li>
            <li class="page tumjudge-isabelle"><a href="https://judge.in.tum.de/isabelle/">Isabelle <span class="description">Proving Contests</span></a></li>
            <li class="page tumjudge-gcpc"><a href="https://judge.in.tum.de/gcpc/">GCPC <span class="description">German Collegiate Programming Contest</span></a></li>
            <li class="page tumjudge-ioide"><a href="https://judge.in.tum.de/ioide/">IOI Germany <span class="description">Preparation for the IOI</span></a></li>
            <li class="page tumjudge-ioiat"><a href="https://judge.in.tum.de/ioiat/">IOI Austria <span class="description">Preparation for the IOI</span></a></li>
            <li class="page tumjudge-challenge"><a href="https://judge.in.tum.de/challenge/">Challenge <span class="description">Yet Another Programming Contest</span></a></li>
            <li class="page tumjudge-mlr"><a href="https://judge.in.tum.de/mlr/">MLR <span class="description">Lecture &ldquo;Machine Learning in Robotics&rdquo;</span></a></li>
          </ul>
        </li>
      <li><a href="https://judge.in.tum.de/isabelle/public/index.php"><span class="glyphicon glyphicon-home"></span> home</a></li>
<li><a href="https://judge.in.tum.de/isabelle/public/scoreboard.php"><span class="glyphicon glyphicon-th-list"></span> scoreboard</a></li>

<form style="display:inline;" action="change_contest.php" method="get" id="selectcontestform">
<input type="hidden" name="cid" value="1"  class="form-control" />
</form>

<script type="text/javascript">
	      function chooseContest(cid) {
	        document.getElementById('selectcontestform').cid.value = cid;
	        document.getElementById('selectcontestform').submit();
              };
              </script><li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><span class="glyphicon glyphicon-flag"></span> contests <span class="caret"></span></a><ul class="dropdown-menu scrollable-menu" role="menu"><li class='header'>Current Contests</li><li class="active"><a href="#" onclick="javascript: chooseContest(1);">Isabelle Coq Proving Contest - September</a></li></ul></li>
      </ul>          
    </div>
  </div>
</nav>