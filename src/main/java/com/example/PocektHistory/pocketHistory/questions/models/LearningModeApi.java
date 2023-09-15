package com.example.PocektHistory.pocketHistory.questions.models;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LearningModeApi {
    private int poprawneOdpowiedzi_HP;
    private int poprawneOdpowiedzi_HS;
    private int poprawneOdpowiedzi_NC;
    private int poprawneOdpowiedzi_PA;
    private int poprawneOdpowiedzi_SC;
    private int poprawneOdpowiedzi_SE;

    private int niepoprawneOdpowiedzi_HP;
    private int niepoprawneOdpowiedzi_HS;
    private int niepoprawneOdpowiedzi_NC;
    private int niepoprawneOdpowiedzi_PA;
    private int niepoprawneOdpowiedzi_SC;
    private int niepoprawneOdpowiedzi_SE;
}
