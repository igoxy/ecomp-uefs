module timing(clk,rst, id_typed, st1, time_max_id, time_max_exb);
	input clk, rst, id_typed;
	input [0:1] st1;
	output reg time_max_id;
	reg start_time,  start_time_ex;
   reg [24:0] counter; 
	output reg time_max_exb;
	
	/*Obs: Não foi possível utilizar um registrador com uma quantidade maior de bits
	para obter um tempo razoável para cada operação da máquina, pois a quantidade máxima
	de LES infelizmente foi atingida.
	 */
	 
	initial begin
		counter = 0;
		start_time = 0;
		start_time_ex  = 0;
		time_max_id = 0;
		time_max_exb = 0;
   end
	
	always @(negedge clk, negedge rst) begin
	
			if(rst == 0) begin
				counter = 0;
				start_time = 0;
				start_time_ex  = 0;
				time_max_id = 0;
				time_max_exb = 0;
			end
			
			else begin
		
				if(st1 == 1) begin
					start_time = 1;
				end
				
				
				if(start_time) begin 
				
					counter = counter + 1;
					
					if(counter [24] && !start_time_ex) begin
						time_max_id = 1;
						start_time = 0;
						counter = 0;
					end 
				
					else if(id_typed && !start_time_ex) begin
						counter = 0;	
						start_time_ex  = 1;		
						
					end
					
					if(start_time_ex) begin
						time_max_exb = counter[24];
										
                 if(counter[24]) counter = 0;
							
						
						

				end
			end
		end
				
				
			
	end
			

endmodule