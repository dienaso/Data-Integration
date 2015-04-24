<!DOCTYPE html>
<html>
<head>
<title>Matrix Admin</title>
<link rel="stylesheet" href="/common/matrix/css/uniform.css" />
<link rel="stylesheet" href="/common/matrix/css/select2.css" />
<script src="/common/matrix/js/jquery.dataTables.min.js"></script> 
<script src="/common/matrix/js/jquery.ui.custom.js"></script> 
<script src="/common/matrix/js/jquery.uniform.js"></script> 
<script src="/common/matrix/js/select2.min.js"></script> 
<script src="/common/matrix/js/matrix.tables.js"></script>
</head>
<body>

<div class="widget-box">
  <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
    <h5>Data table</h5>
  </div>
  <div class="widget-content nopadding">
    <table class="table table-bordered data-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>词条</th>
          <th>拼音</th>
          <th>词性</th>
          <th>近义词</th>
        </tr>
      </thead>
      <tbody>
      	<#list lexicon as list>
	      	<tr class="gradeX">
			  <td>${list.id}</td>
			  <td>${list.word}</td>
			  <td>${list.pinyin}</td>
			  <td>${list.pos}</td>
			  <td>${list.synonym}</td>
	        </tr>
        </#list>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>