module mainCircuit(clk, got_coin, key_one, key_two,  key_three, key_four,
	coin, d, enable_d, back_money, release_product);
	
	input clk, key_one, key_two,  key_three, key_four, got_coin;
	input [2:0] coin;
	output  back_money, release_product;
	output [0:3] enable_d;
	output [7:0] d;
	wire w0, w1,w2,w3, w7, w15;
	wire [1:0] w5, w6;
	wire [0:1] w11;
	wire [0:2] w9;
	wire [3:0] w4, w8, w12, w14; 
	
	//Circuito pra realizar o reset geral depois de um processo de compra.
	rst_overall(.back_money(back_money), .release_product(release_product),
	.time_max_exb(w2), .rst(w0));
	
	
	//Circuito para adicionar o id digitado pelo usuário.
	add_id(.clk(clk) , .rst(w0), .st1(w11), 
	.key_one(key_one) ,.key_two(key_two), .key_three(key_three),
	.key_four(key_four), .row(w5), .col(w6));
	
	
	//Circuito para gerar o próximo estado para a máquina de obter o valor total.
	get_next_state(.coin(coin), .got_coin(got_coin), .st3(w14), .next_state(w12));
	
	
	//Circuito(Máquina) para gerenciar o processo de venda como um todo.
	maquina4(
	.clk(clk), .rst(w0), .id_valid(w7), .val_product(w8)
	,.val_tot(w4), .time_max_exb(w2),.id_typed(w3),
   .back_money(back_money) , .release_product(release_product), 
	.state_now(w9)
	);
	
	//Circuito para gerenciar o tempo de compra.
	timing(.clk(clk), .rst(w0), .id_typed(w3), .st1(w11), .time_max_id(w1), .time_max_exb(w2));
	
	//Circuito para verificar se alguma tecla foi pressionada;
	button_sensor(.key_one(key_one) , .key_two(key_two), .key_three(key_three),
	.key_four(key_four), .apt(w15));
	
	
	//Circuito(Máquina) para auxiliar no processo de adicionar o id digitado.
	get_id(
	.clk(clk), .rst(w0), .apt(w15), .time_max_id(w1),
	.id_typed(w3), .state_now(w11)
	);
	
	
	//Circuito para obter o valor total colocado pelo usuário.
	get_val_tot(.clk(clk), .rst(w0) , .next_state(w12), .val_tot(w4), .got_coin(got_coin),
	.state_now(w14));
	
	//Circuito para comparar o id digitado pelo usuário com os produtos existentes.
	comparator_id(.id_typed(w3), .col(w6), .row(w5), .id_valid(w7), .val_product(w8));
	
	//Circuito para exibir o processo de venda no display.
	controlOutputDisplay(.state(w9), .val_tot(w12), .row(w5), .col(w6), 
		.d(d), .enable_d(enable_d), .clk(clk));
	
endmodule