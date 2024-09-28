module  mainCircuit(
sen0,
sen1, 
sen2, 
sen3, 
sen4,
button0,
alarme,
seg_A,
seg_B,
seg_C,
seg_D,
seg_E,
seg_F,
seg_G);

input sen0, sen1, sen2, sen3, sen4, button0;
wire w1, out0, out1, out2;
output seg_A, seg_B, seg_C, seg_D, seg_E, seg_F, seg_G, alarme;

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
	
	controlOutputDis dcfd_inst(
		.BUTTON(button0),
		.ENTRADA_AC(out0),
		.ENTRADA_CO(out1),
		.ENTRADA_RE(out2),
		.SEG_A(seg_A),
		.SEG_B(seg_B),
		.SEG_C(seg_C),
		.SEG_D(seg_D),
		.SEG_E(seg_E),
		.SEG_F(seg_F),
		.SEG_G(seg_G),
	);
	
	controlOutputAL(.button(button0), .AC(out0), .AL(alarme));
	
	
endmodule