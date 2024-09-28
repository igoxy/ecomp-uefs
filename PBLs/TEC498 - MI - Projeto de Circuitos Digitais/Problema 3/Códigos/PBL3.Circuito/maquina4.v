module maquina4(

clk, rst,id_valid, val_product
,val_tot, time_max_exb,id_typed,
back_money , release_product, state_now

);

	input  clk, rst, id_typed,  time_max_exb, id_valid;
	input  [3:0] val_tot, val_product;
	output reg back_money;
	output reg release_product;
	output reg [0:2] state_now;


	reg [0:2] state;
	
	
	parameter zzz = 3'b000, 
				 validate_id = 3'b001,
				 validate_coin = 3'b010,
				 all_ok = 3'b011,
				 error1 = 3'b100,
				 error2 = 3'b101;
				 
	
	
	initial begin 
	
		state <= zzz;
		state_now <= 0;
		back_money = 0;
		release_product = 0;
		
	end
	
	always @(posedge clk, negedge rst) begin
		if(rst == 0) begin
			state <= zzz;
			back_money = 0;
			release_product = 0;
		end
		
		else begin
			case(state)
			
				zzz: begin
					if(id_typed) state <= validate_id;
					
					else state <= zzz;
				end		
							
						
				validate_id: begin
				
					   if(id_valid)begin
							if(time_max_exb) state <= validate_coin;
							else state <= validate_id;
						end	
						
						else begin
							if(time_max_exb) state <= error1;
							else state <= validate_id;
						end
																	
				end
				
					
					
				
				validate_coin: begin
				
					if(time_max_exb) begin
					
						if(val_tot == val_product) state <= all_ok;
						else state <= error2;
						
					end
					
					else state <= validate_coin;
					
					
				end
				
				all_ok: begin
					if(time_max_exb) state <= zzz;
					
					
					else state <= all_ok;
					
					release_product = 1;
					
				end
				
				error1: begin
				
					if(time_max_exb) state <= zzz;
					
					
					else state <= error1;
					
					
				
					
				end
				
				error2: begin
				
					if(time_max_exb) state <= zzz;
					
					
					else state <= error2;
					
					back_money = 1;
			
					
				
					
				end
				
			endcase
			
		end
		
	end
	
	always@(state) state_now <= state;
	
		

endmodule