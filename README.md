# 🎮 Jogo da Forca - Laboratório de Orientação a Objetos

**Disciplina:** Laboratório de OO  
**Professor:** Mark Douglas Jacyntho  

---

## 📌 Descrição

Este projeto é a implementação em Java do clássico **Jogo da Forca**, adaptado com regras e requisitos específicos para fins didáticos da disciplina.

---

## 🧠 Regras do Jogo

- O jogador tenta adivinhar letras de **uma ou mais palavras escondidas**.
- A cada erro, um novo pedaço do **boneco da forca** aparece.  
  O jogador pode errar **no máximo 10 letras**.
- As **palavras** vêm de um **banco de dados**, que pode ser preenchido pelo próprio jogador.
- Cada palavra pertence a um **tema**.
- A cada rodada, o jogo sorteia aleatoriamente:
  - Um **tema**
  - **1, 2 ou 3 palavras**, todas do mesmo tema

---

## 🎯 Objetivo

- Acertar todas as letras das palavras sorteadas.
- **Acertos** revelam a letra nas posições corretas.
- **Erros** são mostrados num quadro e acrescentam partes ao boneco.

---

## 🏆 Sistema de Pontuação

- Ao acertar todas as palavras da rodada:
  - Ganha **100 pontos**
  - +15 pontos por **cada letra ainda encoberta** no momento do acerto
- **Atenção:** só é permitido **arriscar** (tentar adivinhar todas as palavras) **uma única vez por rodada**
- A rodada termina quando:
  - O jogador arrisca (acertando ou errando),
  - Ou descobre todas as letras sem arriscar,
  - Ou comete 10 erros

---

## 🗃️ Armazenamento

- O nome do jogador e sua pontuação por rodada são armazenados.
- Esses dados compõem o **ranking dos maiores escores**.

---

## 🛠️ Estrutura Técnica

Implementado em **Java**, com os seguintes conceitos e padrões:

- **Classes** e **atributos**
- **Operações** e **regras de negócio (invariantes)**
- **Relacionamentos entre objetos**
- **Repositórios**
- **Fábricas (Factory)**
- **Serviços (Service)**

---

---

## 👩‍💻 Integrantes

> Bruna Souza [@BrunaSoug] 
---

## 📎 Licença

Este projeto é de uso acadêmico e segue as diretrizes da disciplina de Laboratório de OO.
