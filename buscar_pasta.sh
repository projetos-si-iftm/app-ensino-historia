#!/bin/bash

echo "🔎 Procurando por commits que contêm a pasta 'frontend-react/frontend/'..."
echo

# Percorre todos os commits
git rev-list --all | while read commit; do
  # Verifica se a pasta existia no commit
  if git ls-tree -r --name-only "$commit" | grep -q "src/frontend-react/"; then
    echo "========================================"
    echo "✓ Commit:    $commit"
    
    # Mostrar detalhes do commit
    git show -s --format="Autor:      %an <%ae>%nData:       %ad%nMensagem:   %s" "$commit"
    
    echo "Arquivos encontrados:"
    git ls-tree -r --name-only "$commit" | grep "src/frontend-react/" | sed 's/^/  - /'
    echo
  fi
done
