module controlOutputDisplay(
state,
val_tot, 
row,
col,
d,
enable_d,
clk);

	input [0:2] state;
	input [3:0] val_tot;
	input [1:0] row, col;
	input clk;
	output reg  [7:0] d;
	reg  [7:0] d1,d2,d3,d4;
	reg [13:0] clk_counter;
	reg [1:0] interator_d;
	output reg [0:3] enable_d;// ativa um dígito por vez
	
	initial begin
	
		clk_counter = 0;
		interator_d = 0;
		d1 = 8'b00000000;
		d2 = 8'b00000000;
		d3 = 8'b00000000;
		d4 = 8'b00000000;
	end
	
	always @(state) begin
	
		case (state) 
		
			3'b000: begin // Estado zzz(espera)
				d1 = 8'b10000000;
				d2 = 8'b10000000;
				d3 = 8'b10000000;
				d4 = 8'b10000000;
			end
			
			3'b001: begin // Estado para validar o id
				
					case ({row, col}) //exibe o id no display
					
						4'b0000: begin 
						
							d1 = 8'b00000110;
							d2 = 8'b00000110;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
						end
						
						4'b0001: begin 
						
							d1 = 8'b01011011;
							d2 = 8'b00000110;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
							
							
						end
						
						4'b0010: begin 
				
							d1 = 8'b01001111;
							d2 = 8'b00000110;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
						end
						
						4'b0011: begin 
						
							d1 = 8'b01100110;
							d2 = 8'b00000110;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
							
						end
						
						4'b0100: begin 
						
							d1 = 8'b00000110;
							d2 = 8'b01011011;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
						end
						
						4'b0101: begin 
						
							d1 = 8'b01011011;
							d2 = 8'b01011011;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
						end
						
						4'b0110: begin 
						
							d1 = 8'b01001111;
							d2 = 8'b01011011;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
						end
						
						4'b0111: begin 
						
							d1 = 8'b01100110;
							d2 = 8'b01011011;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
						end
						
					
						
						4'b1000: begin 
							
							d1 = 8'b00000110;
							d2 = 8'b01001111;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
						
						end
						
						4'b1001: begin 
							
							d1 = 8'b01011011;
							d2 = 8'b01001111;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
						
						end
						
						4'b1010: begin 
							
							d1 = 8'b01001111;
							d2 = 8'b01001111;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
						
						end
						
						4'b1011: begin 
							
							d1 = 8'b01100110;
							d1 = 8'b01001111;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
						
						end
						
						4'b1100: begin 
							
							d1 = 8'b00000110;
							d2 = 8'b01100110;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
						
						end

						4'b1101: begin 
							
							d1 = 8'b01011011;
							d2 = 8'b01100110;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
						
						end
						
						4'b1110: begin 
							
							d1 = 8'b01001111;
							d2 = 8'b01100110;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
						
						end
						
						4'b1111: begin 
							
							d1 = 8'b01100110;
							d2 = 8'b01100110;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
						
						end
						
						default: begin
							d1 = 8'b00000000;
							d2 = 8'b00000000;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
						end
						
						
						
						
						
					 endcase
					
			
					
				end
				
				3'b010: begin // Estado para validar a moeda
					case(val_tot)
						// exibe no display o valor total
						
						4'b0000: begin
							d1 = 8'b00111111;
							d2 = 8'b00111111;
							d3 = 8'b10111111;
							d4 = 8'b00000000;
						end
						
						4'b0001: begin
							d1 = 8'b01101101;
							d2 = 8'b01011011;
							d3 = 8'b10111111;
							d4 = 8'b00000000;
							
						end
						
						4'b0010: begin
							d1 = 8'b00111111;
							d2 = 8'b01101101;
							d3 = 8'b10111111;
							d4 = 8'b00000000;
							
						end
						
						4'b0011: begin
							d1 = 8'b01101101;
							d2 = 8'b00000111;
							d3 = 8'b10111111;
							d4 = 8'b00000000;
							
						end
						
						4'b0100: begin
							d1 = 8'b00111111;
							d2 = 8'b00111111;
							d3 = 8'b10000110;
							d4 = 8'b00000000;
							
						end
						
						4'b0101: begin
							d1 = 8'b01101101;
							d2 = 8'b01011011;
							d3 = 8'b10000110;
							d4 = 8'b00000000;
							
						end
						
						4'b0110: begin
							d1 = 8'b00111111;
							d2 = 8'b01101101;
							d3 = 8'b10000110;
							d4 = 8'b00000000;
							
						end
						
						4'b0111: begin
							d1 = 8'b01101101;
							d2 = 8'b00000111;
							d3 = 8'b10000110;
							d4 = 8'b00000000;
							
						end
						
						4'b1000: begin
							d1 = 8'b00111111;
							d2 = 8'b00111111;
							d3 = 8'b11011011;
							d4 = 8'b00000000;
							
						end
						
						default: begin
							d1 = 8'b00000000;
							d2 = 8'b00000000;
							d3 = 8'b00000000;
							d4 = 8'b00000000;
							
						end
						
					endcase
				end
				
				3'b011: begin // Estado caso tenha dado certo a compra
					d1 = 8'b01111111;
					d2 = 8'b01111111;
					d3 = 8'b01111111;
					d4 = 8'b01111111;
				end
				
				3'b100: begin // Estado caso o id seja inválido
					d1 = 8'b00000110;
					d2 = 8'b10110011;
					d3 = 8'b10110011;
					d4 = 8'b01111001;
				end
				
				3'b101: begin // Estado caso o valor seja inválido
					d1 = 8'b01011011;
					d2 = 8'b10110011;
					d3 = 8'b10110011;
					d4 = 8'b01111001;
				end
				
				default: begin
					d1 = 8'b00000000;
					d2 = 8'b00000000;
					d3 = 8'b00000000;
					d4 = 8'b00000000;
							
				end
			
			
			
		endcase
			
	end
	
	always@(posedge clk) clk_counter = clk_counter + 1;
	
	always @(posedge clk_counter[13])begin
	
		
		case(interator_d) // ativa um display por vez na frquencia de 76Hz
			2'b00: d = d1;
			2'b01: d = d2;
			2'b10: d = d3;
			2'b11: d = d4;
			default: d = d1;
		endcase
		
		enable_d <= 4'b0000;
		enable_d[interator_d] <= 1; // ativa o dígito correto do display
		
		
		interator_d = interator_d + 1;
			
		
			
		
		
	end
	
	
	
endmodule