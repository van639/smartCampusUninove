<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Sarabun:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Sarabun:wght@300;400;500;600&family=Abhaya+Libre:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/home.css">
    <link rel="stylesheet" href="assets/css/cadastro.css">
    <link rel="stylesheet" href="assets/css/estoque.css">
    <script src="assets/js/tabelaEstoque.js" defer></script>
    <script src="assets/js/filtro.js" defer></script>
    <script src="assets/js/menu.js" defer></script>
    <title>Smart Campus</title>
</head>

<body>
    <div id="container-geral">
        <div id="menu-logo">
            <div id="logo">
                <img src="assets/img/fotos/logo.png" alt="logo">
            </div>
            <nav>
                <ul>
                    <li onclick="trocarConteudo('home')">
                        <a href="#">
                            <img src="assets/img/icons/Home.png" alt="Home">HOME
                        </a>
                    </li>
                    <li onclick="trocarConteudo('cadastro')">
                        <a href="#">
                            <img src="assets/img/icons/Cadastro.png" alt="Cadastro">CADASTRO
                        </a>
                    </li>
                    <li onclick="trocarConteudo('estoque')">
                        <a href="#">
                            <img src="assets/img/icons/Estoque.png" alt="Estoque">ESTOQUE
                        </a>
                    </li>
                </ul>
            </nav>
        </div>

        <div id="conteudo">
            <!-- Filtro Estoque -->
            <div id="filtro-estoque">
                <div id="container-filtro">
                    <label>
                        <input id="pesquisa-produto" placeholder="Escreva o nome do produto" />
                        <img src="assets/img/icons/search.png" style="margin-left: -50px; cursor: pointer;" />
                    </label>
                    <div id="select-categoria">
                        <select id="categoria-select">
                            <option value="null">Selecione a categoria</option>
                        </select>
                    </div>
                    <div id="filtro">
                        <img src="assets/img/fotos/filter.png" alt="">
                        Filtro (2)
                    </div>
                </div>
            </div>

            <!-- Home -->
            <div id="container-home">
                <div id="scrool-home">
                    <h1>Bem-vindo ao SmartCampus</h1>
                    <p class="txt-missao">
                        <br>Missão<br>
                        Desenvolver soluções digitais inovadoras e acessíveis voltadas à gestão de estoque em empresas de pequeno e médio porte, proporcionando maior controle, organização e eficiência operacional, por meio da implementação de um sistema automatizado que atenda às reais necessidades do mercado, contribuindo para o crescimento dos negócios.
                        
                        <br><br>Visão<br>
                        Ser reconhecida como referência nacional no desenvolvimento de sistemas inovadores para a  gestão de estoque para pequenas e médias empresas, destacando-se pela entrega de soluções tecnológicas eficazes, com foco em usabilidade, escalabilidade e impacto direto na melhoria da administração empresarial.

                        <br><br>Valores<br>
                        A empresa SmartCampus foi criada com o intuito de inovar, aprimorar e criar soluções tecnológicas que atendam às demandas do mercado e facilitem o dia a dia das empresas. 
                        Nossas ações são direcionadas para atender às necessidades de nossos clientes com software de qualidade, garantindo resultados e superando expectativas. Temos compromisso com a usabilidade e a eficiência em tudo o que fazemos, buscando sempre alcançar a excelência em nossos processos.
                    </p>
                    <div id="container-img-apresentacao">
                        <img src="assets/img/fotos/apresentacao.png" alt="">
                        <div id="container-txt-apresentacao">
                            <h2>Automatize seu controle de estoque e ganhe mais tempo!</h2>
                            <p>Nosso sistema de gestão de estoque permite cadastrar facilmente seus produtos, oferecendo uma visão completa e organizada do seu inventário. Você pode visualizar todos os itens cadastrados, com a possibilidade de filtrar por nome ou categoria, facilitando a localização dos produtos. Além disso, é possível verificar a data de vencimento, o valor de cada produto e a quantidade disponível de forma rápida e prática, garantindo um controle eficiente e atualizado do seu estoque.</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Cadastro -->
            <div id="container-cadastro">
                <h1>CADASTRO DE PRODUTOS</h1>
                <form name="cadastrar" >
                    <div id="container-label">
                        <div id="left">
                            <label>
                                Nome Produto
                                <input type="text" placeholder="Escreva o nome" name="nome" required>
                            </label>
                            <label>
                                Categoria
                                <select name="category" id="categoria" required>
                                    <option value="null">Selecione a categoria</option>
                                </select>
                            </label>
                            <label>
                                Observação
                                <input type="text" placeholder="Escreva uma observação" name="observacao">
                            </label>
                        </div>
                        <div id="right">
                            <label>
                                Valor da Unidade
                                <input type="text" placeholder="R$" name="valor" required>
                            </label>
                            <label>
                                Data de Validade
                                <input type="date" name="validade" required />
                            </label>
                            <label>
                                Quantidade
                                <input type="number" name="quantidade" required>
                            </label>
                        </div>
                    </div>
                    <div id="container-btn">
                        <button type="reset" class="btn" style="background-color: red;">
                            CANCELAR
                            <img src="assets/img/fotos/Close.png" alt="">
                        </button>
                        <button type="button" onclick="cadastrarProduto()" class="btn" id="btn-cadastrar" style="background-color: rgb(36, 232, 36);">
                            <span>CADASTRAR</span>
                            <img src="assets/img/fotos/Checkmark.png" alt="">
                        </button>
                        <input type="hidden" name="operacao" value="inserir" />
                    </div>
                </form>
                <!-- Exibição de alerta em JavaScript -->
                <%
                    String sucesso = (String) request.getAttribute("mensagem_sucesso");
                    String erro = (String) request.getAttribute("mensagem_erro");
                %>
                <script>
                    <% if (sucesso != null) { %>
                        alert("<%= sucesso %>");
                    <% } else if (erro != null) { %>
                        alert("<%= erro %>");
                    <% } %>
                        
                   
                </script>
            </div>

            <!-- Estoque -->
            <div id="container-estoque">
                <table>
                    <thead>
                        <tr>
                            <th>Produto</th>
                            <th>Quantidade</th>
                            <th>Categoria</th>
                            <th>Data Validade</th>
                            <th>Valor Unidade</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="product-list">
                        <!-- preenchido por JavaScript -->
                    </tbody>
                </table>
                <div id="pagination">
                    <span id="pageNumber">Página 1</span>
                    <button id="prevPage">
                        <img src="assets/img/fotos/seta-esquerda.png" alt="">
                    </button>
                    <button id="nextPage">
                        <img src="assets/img/fotos/seta-direita.png" alt="">
                    </button>
                </div>
            </div>
        </div>
    </div>
    <script src="assets/js/editar.js"></script>
    <%
    String mostrarEstoque = (String) request.getAttribute("mostrar_estoque");
    %>
    <script>
        window.addEventListener("DOMContentLoaded", () => {
          <% if (mostrarEstoque != null && mostrarEstoque.equals("true")) { %>
            trocarConteudo("estoque");
          <% }  %>
        });
      </script>
    
</body>

</html>
