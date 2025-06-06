function converterDataParaInputDate(data) {
    if (!data) return '';
    const partes = data.split('/');
    if (partes.length !== 3) return '';
    const [dia, mes, ano] = partes;
    return `${ano}-${mes.padStart(2, '0')}-${dia.padStart(2, '0')}`;
}

function carregarCategorias() {
    return fetch('/crudmvc_mm-main/ListarCategorias')
        .then(resp => {
            if (!resp.ok) throw new Error('Erro ao carregar categorias');
            return resp.json();
        })
        .then(cats => {
            const sel = document.querySelector('#container-cadastro select[name="category"]');
            sel.innerHTML = '<option value="null">Selecione</option>';
            cats.forEach(c => {
                const opt = document.createElement('option');
                opt.value = c.id;
                opt.textContent = c.nome;
                sel.appendChild(opt);
            });
        })
        .catch(err => console.error(err));
}

function preencherFormularioEditar(produto) {
    if (!produto) return;

    document.querySelector('input[name="nome"]').value = produto.nome || '';
    document.querySelector('input[name="valor"]').value = produto.valor || '';
    document.querySelector('input[name="quantidade"]').value = produto.quantidade || '';

    if (produto.validade) {
        document.querySelector('input[name="validade"]').value = converterDataParaInputDate(produto.validade);
    } else {
        document.querySelector('input[name="validade"]').value = '';
    }

    const campoObservacao = document.querySelector('#container-cadastro input[name="observacao"]');
    if (campoObservacao) {
        campoObservacao.value = produto.observacao || '';
    }

    // Preencher categoria diretamente
    const selectCategoria = document.querySelector('#container-cadastro select[name="category"]');
    selectCategoria.value = produto.id_categoria || 'null';

    const botaoCadastrar = document.getElementById('btn-cadastrar');
    if (botaoCadastrar) {
        botaoCadastrar.querySelector('span').textContent = 'EDITAR';
        botaoCadastrar.setAttribute('data-id-edicao', produto.id);
    }
}

function limparFormularioCadastro() {
    document.querySelector('#container-cadastro input[name="nome"]').value = '';
    document.querySelector('#container-cadastro select[name="category"]').value = 'null';

    const campoObservacao = document.querySelector('input[name="observacao"]') || document.querySelector('textarea[name="observacao"]');
    if (campoObservacao) {
        campoObservacao.value = '';
    }

    document.querySelector('#container-cadastro input[name="valor"]').value = '';
    document.querySelector('#container-cadastro input[name="validade"]').value = '';
    document.querySelector('#container-cadastro input[name="quantidade"]').value = '';

    const botaoCadastrar = document.getElementById('btn-cadastrar');
    if (botaoCadastrar) {
        botaoCadastrar.querySelector('span').textContent = 'CADASTRAR';
        botaoCadastrar.removeAttribute('data-id-edicao');
    }
}

// Botão "Limpar"
document.querySelector('#container-cadastro .btn[style*="red"]')?.addEventListener('click', limparFormularioCadastro);

// Ao carregar a tela de edição (em caso de refresh)
window.addEventListener('DOMContentLoaded', () => {
    const produtoEdit = localStorage.getItem('produtoParaEditar');
    if (produtoEdit) {
        const produto = JSON.parse(produtoEdit);
        carregarCategorias().then(() => preencherFormularioEditar(produto));
        localStorage.removeItem('produtoParaEditar');
    }
});

function cadastrarProduto() {
    const form = document.forms["cadastrar"];
    const botao = document.getElementById("btn-cadastrar");
    const acao = botao.querySelector("span").textContent.trim();

    const dados = new URLSearchParams();
    dados.append("nome", form.nome.value);
    dados.append("category", form.category.value);
    dados.append("observacao", form.observacao.value);
    dados.append("valor", form.valor.value);
    dados.append("validade", form.validade.value);
    dados.append("quantidade", form.quantidade.value);

    let url = "Inserir"; // padrão é cadastrar
    let mensagemSucesso = "Produto cadastrado com sucesso!";

    if (acao === "EDITAR") {
        const idProduto = botao.getAttribute("data-id-edicao");
        dados.append("id", idProduto);
        url = "Editar"; // muda para servlet de edição
        mensagemSucesso = "Produto editado com sucesso!";
    } else {
        dados.append("operacao", form.operacao.value); // para cadastro
    }

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: dados.toString()
    })
    .then(response => {
        if (!response.ok) throw new Error("Erro na resposta");
        return response.text();
    })
    .then(data => {
        alert(mensagemSucesso);
        // Aqui você pode chamar uma função para recarregar a tabela
        // ou trocar para a aba de estoque automaticamente
        form.reset();
        botao.querySelector("span").textContent = "CADASTRAR";
        botao.removeAttribute("data-id-edicao");
        
        location.reload();
        trocarConteudo('estoque');
        
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao salvar produto.");
    });
}



