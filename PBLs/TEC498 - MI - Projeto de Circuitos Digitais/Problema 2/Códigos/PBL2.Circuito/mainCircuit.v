module  mainCircuit(
sen0,
sen1, 
sen2, 
sen3, 
sen4,
button0,
alarme,
clk,
COLUNA,
LINHA);

input sen0, sen1, sen2, sen3, sen4, button0, clk;
wire w1, out0, out1, out2;
output [0:4] COLUNA;
output [0:6] LINHA;
output alarme;

	controlOutputAC act_inst(
		.snA0(sen0),
		.snA1(sen1),
		.snA2(sen2),
		.snA3(sen3),
		.snA4(sen4),
		.OUT_AC(out0)
		);

	controlOutputCO cptd_inst(
		.sn0(sen0),
		.sn1(sen1),
		.sn2(sen2),
		.sn3(sen3),
		.sn4(sen4),
		.OUT_CO(out1)
		);
		
	
	
	controlOutputRE rgtd_inst(
		.AC(out0),
		.CO(out1),
		.OUT_RE(out2)
	);
	
	controlMatriz(
		.clk(clk),
		.A(out0),
		.C(out1),
		.R(out2),
		.button(button0),
		.COLUNA(COLUNA),
		.LINHA(LINHA)
	
	);
	
	controlOutputAL(.button(button0), .AC(out0), .AL(alarme));
	
	
endmodule