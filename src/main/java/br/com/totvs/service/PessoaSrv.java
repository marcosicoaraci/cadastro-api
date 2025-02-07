package br.com.totvs.service;

import br.com.totvs.dto.request.PessoaRequestDTO;
import br.com.totvs.dto.response.PessoaResponseDTO;
import br.com.totvs.entity.Pessoa;
import br.com.totvs.exceptions.ObjetoNaoEncontradoException;
import br.com.totvs.repository.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaSrv {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa getUm(Integer id){
        return this.pessoaRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto com id "+id+" não encontrado"));
    }

    public List<Pessoa> listarTodos(Pageable pageable){
        List<Pessoa> resposta = pessoaRepository.listar(pageable);
        if (resposta.isEmpty()) {
            throw new ObjetoNaoEncontradoException("A lista não possui itens");
        }

        return resposta;
    }

    public PessoaResponseDTO salvar(PessoaRequestDTO pessoaRequestDTO){
        try {
            ModelMapper modelMapper = new ModelMapper();
            Pessoa pessoa = modelMapper.map(pessoaRequestDTO, Pessoa.class);
            pessoaRepository.save(pessoa);
            PessoaResponseDTO resposta = modelMapper.map(pessoa, PessoaResponseDTO.class);
            return resposta;
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro desconhecido");
        }
    }

    public void excluir (Integer id){
        try {
            pessoaRepository.deleteById(id);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao excluir objeto: "+e);
        }
    }



}
