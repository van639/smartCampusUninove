let contadorId = 1;

const produtos = [];
const itensPorPagina = 8;
let paginaAtual = 1;
let produtosFiltrados = [...produtos];

function ListarProdutos() {
    fetch('/crudmvc_mm-main/ListarProdutos')
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao carregar produtos");
            }
            return response.json();
        })
        .then(data => {
            produtos.length = 0;
            produtos.push(...data);
            produtosFiltrados = [...produtos];
            carregarProdutos();
            controlarPaginacao();
        })
        .catch(err => {
            console.error("Erro ao carregar produtos:", err);
        });
}

function carregarProdutos() {
    const tbody = document.getElementById('product-list');
    const paginaInicial = (paginaAtual - 1) * itensPorPagina;
    const produtosPagina = produtosFiltrados.slice(paginaInicial, paginaInicial + itensPorPagina);

    tbody.innerHTML = '';

    produtosPagina.forEach((produto, index) => {
        const tr = document.createElement('tr');
        const modalId = `reaction-modal-${index}`;

        tr.innerHTML = `
            <td>${produto.nome}</td>
            <td>${produto.quantidade}</td>
            <td>${produto.categoria}</td>
            <td>${produto.validade}</td>
            <td>${produto.valor}</td>
            <td class="img-opcoes">
                <img src="assets/img/icons/opcoes.png" alt="Opções" class="like-btn" data-modal="${modalId}" />
                <div class="reaction-modal" id="${modalId}">
                    <span class="editar" data-id="${produto.id}">
                      Editar
                      <img src="assets/img/icons/edit-3.png" alt="Editar">
                    </span>
                    <span class="excluir" data-id="${produto.id}">
                        Excluir
                        <img src="assets/img/icons/trash-2.png" alt="Excluir">
                    </span>
                </div>
            </td>
        `;

        tbody.appendChild(tr);

        tr.querySelector('.editar').addEventListener('click', () => {
            const produtoId = tr.querySelector('.editar').getAttribute('data-id');
            const produto = produtos.find(p => p.id == produtoId);
            
            trocarConteudo('cadastro');
            
            carregarCategorias().then(() => preencherFormularioEditar(produto));
        });

        const likeBtn = tr.querySelector('.like-btn');
        const reactionModal = tr.querySelector(`#${modalId}`);

        likeBtn.addEventListener('mouseenter', () => {
            reactionModal.style.display = 'block';
        });

        reactionModal.addEventListener('mouseenter', () => {
            reactionModal.style.display = 'block';
        });

        likeBtn.addEventListener('mouseleave', () => {
            setTimeout(() => {
                if (!reactionModal.matches(':hover')) {
                    reactionModal.style.display = 'none';
                }
            }, 100);
        });

        reactionModal.addEventListener('mouseleave', () => {
            reactionModal.style.display = 'none';
        });

        tr.querySelector('.excluir').addEventListener('click', () => {
            mostrarModalConfirmacao(tr.querySelector('.excluir').getAttribute('data-id'));
        });
    });

    const totalPaginas = Math.ceil(produtosFiltrados.length / itensPorPagina);
    document.getElementById('pageNumber').textContent = `${paginaAtual}/${totalPaginas}`;
}

function controlarPaginacao() {
    const totalPaginas = Math.ceil(produtosFiltrados.length / itensPorPagina);

    document.getElementById('prevPage').addEventListener('click', () => {
        if (paginaAtual > 1) {
            paginaAtual--;
            carregarProdutos();
        }
    });

    document.getElementById('nextPage').addEventListener('click', () => {
        if (paginaAtual < totalPaginas) {
            paginaAtual++;
            carregarProdutos();
        }
    });
}

function mostrarModalConfirmacao(idProduto) {
    const modal = document.createElement('div');
    modal.classList.add('modal');
    modal.innerHTML = `
        <div class="modal-content">
            <p>Tem certeza que deseja excluir esse produto?</p>
            <button id="confirmarSim">Sim</button>
            <button id="confirmarNao">Não</button>
        </div>
    `;
    document.body.appendChild(modal);

    document.getElementById('confirmarSim').addEventListener('click', () => {
        excluirProduto(idProduto);
        document.body.removeChild(modal);
    });

    document.getElementById('confirmarNao').addEventListener('click', () => {
        document.body.removeChild(modal);
    });
}

function excluirProduto(id) {
    fetch('/crudmvc_mm-main/Excluir', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `id=${id}`
    })
    .then(response => {
        if (!response.ok) throw new Error('Erro ao excluir produto');
        return response.text();
    })
    .then(() => {
        alert('Produto excluído com sucesso!');
        produtosFiltrados = produtosFiltrados.filter(p => p.id !== parseInt(id));
        carregarProdutos();
    })
    .catch(error => {
        alert('Erro ao excluir produto: ' + error.message);
        console.error(error);
    });
}

