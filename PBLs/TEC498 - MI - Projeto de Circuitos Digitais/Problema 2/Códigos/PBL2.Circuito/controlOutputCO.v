module controlOutputCO(
	input sn0,
	input sn1,
	input sn2,
	input sn3,
	input sn4,
	output OUT_CO
);
	wire T0, T1, T2, T3, T4;
	wire sn0bar, sn1bar, sn2bar, sn3bar, sn4bar;
	

	
	and And0 (T0, !sn0, sn1, sn2, sn3, sn4);
	and And1 (T1, sn0, !sn1, sn2, sn3, sn4);
	and And2 (T2, sn0, sn1, !sn2, sn3, sn4);
	and And3 (T3, sn0, sn1, sn2, !sn3, sn4);
	and And4 (T4, sn0, sn1, sn2, sn3, !sn4);
	
	or Or0 (OUT_CO, T0, T1, T2, T3, T4);
	
endmodule 