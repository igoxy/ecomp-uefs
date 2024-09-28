module rst_overall(back_money, release_product, time_max_exb, rst);
	input  back_money, release_product, time_max_exb;
	output reg rst;
	
	initial rst = 1;
	always@(*) begin
		if(back_money == 1|| release_product == 1) begin
			if(time_max_exb) rst = 0;
		end
		else rst = 1;
	end
endmodule