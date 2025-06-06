const categorias = [
    'Bebida',
    'Alimento',
    'Eletrônicos',
    'Higiene Pessoal',
    'Vestuário',
    'Casa',
    'Acessórios'
];

function preencherSelectCategorias() {
    const select = document.getElementById('categoria-select');
    select.innerHTML = '<option value="null">Selecione a categoria</option>';

    categorias.forEach(categoria => {
        const option = document.createElement('option');
        option.value = categoria.toLowerCase()
                                .normalize("NFD")
                                .replace(/[\u0300-\u036f]/g, "")
                                .replace(/\s+/g, '-');
        option.textContent = categoria;
        select.appendChild(option);
    });
}

// 🔁 Função combinada para aplicar todos os filtros
function aplicarFiltros() {
    const pesquisaInput = document.getElementById('pesquisa-produto').value.toLowerCase();
    const categoriaSelecionada = document.getElementById('categoria-select').value;

    produtosFiltrados = produtos.filter(produto => {
        const nomeValido = produto.nome.toLowerCase().includes(pesquisaInput);

        const categoriaNormalizada = produto.categoria?.toLowerCase()
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .replace(/\s+/g, '-');

        const categoriaValida = (categoriaSelecionada === "null") || categoriaNormalizada === categoriaSelecionada;

        return nomeValido && categoriaValida;
    });

    paginaAtual = 1;
    carregarProdutos();
}

// 🔁 Aplica os dois filtros ao mesmo tempo quando qualquer um muda
document.getElementById('categoria-select').addEventListener('change', aplicarFiltros);
document.getElementById('pesquisa-produto').addEventListener('input', aplicarFiltros);

document.addEventListener('DOMContentLoaded', () => {
    ListarProdutos();
    preencherSelectCategorias();
});
