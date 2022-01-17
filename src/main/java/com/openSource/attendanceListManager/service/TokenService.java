package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.entity.Token;
import com.openSource.attendanceListManager.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final InspectorService inspectorService;

    public Token findByName(String name){
        return tokenRepository.findByName(name);
    }

    public void addToken(String tokenName, Inspector inspector){

        LocalDateTime date = LocalDateTime.now();
        LocalDateTime date1 = date.plusHours(1);

        Token token = new Token();
        token.setName(tokenName);
        token.setDate(date1);
        tokenRepository.save(token);

        Set<Token> tokenSet = inspector.getTokens();
        tokenSet.add(token);
        inspector.setTokens(tokenSet);
        inspectorService.addInspector(inspector);
    }
}
