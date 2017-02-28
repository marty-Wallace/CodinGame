use std::io;

#[allow(dead_code)]

macro_rules! print_err {
    ($($arg:tt)*) => (
        {
            use std::io::Write;
            writeln!(&mut ::std::io::stderr(), $($arg)*).ok();
        }
    )
}

macro_rules! parse_input {
    ($x:expr, $t:ident) => ($x.trim().parse::<$t>().unwrap())
}

#[derive(Debug, Clone, Copy)]
struct Factory {
    id: usize,
    owner: i8,
    population: i32,
    prod: i32,
}

#[derive(Debug, Clone, Copy)]
struct Troop {
    id: usize,
    owner: i8,
    pop: i32,
    from: i32,
    target: i32,
    turns: i32,
}

#[allow(dead_code)]
#[allow(unused_variables)]
fn main() {

    let mut input_line = String::new();
    io::stdin().read_line(&mut input_line).unwrap();
    let factory_count = parse_input!(input_line, usize); // the number of factories

    let mut distances = vec![vec![0_i32; factory_count]; factory_count];

    let mut input_line = String::new();
    io::stdin().read_line(&mut input_line).unwrap();
    let link_count = parse_input!(input_line, i32); // the number of links between factories

    for i in 0..link_count as usize {
        let mut input_line = String::new();
        io::stdin().read_line(&mut input_line).unwrap();

        let inputs = input_line.split(" ").collect::<Vec<_>>();
        let factory_1 = parse_input!(inputs[0], usize);
        let factory_2 = parse_input!(inputs[1], usize);
        let distance = parse_input!(inputs[2], i32);
        distances[factory_1][factory_2] = distance;
        distances[factory_2][factory_1] = distance;

    }

    // game loop
    loop {
        let mut input_line = String::new();
        io::stdin().read_line(&mut input_line).unwrap();
        print_err!("{}", input_line);
        let entity_count = parse_input!(input_line, usize); // the number of entities (e.g. factories and troops)

        let mut factories = Vec::with_capacity(factory_count);
        let mut troops = Vec::with_capacity(entity_count - factory_count);

        for i in 0..entity_count as usize {
            let mut input_line = String::new();
            io::stdin().read_line(&mut input_line).unwrap();
            let inputs = input_line.split(" ").collect::<Vec<_>>();
            let entity_id = parse_input!(inputs[0], usize);
            let entity_type = inputs[1].trim().to_string();
            let arg_1 = parse_input!(inputs[2], i8);  // owner -1, 0, or 1
            let arg_2 = parse_input!(inputs[3], i32); // num of cyborgs or where troop leaves from
            let arg_3 = parse_input!(inputs[4], i32); // fact prod 0-3 or where troop headed
            let arg_4 = parse_input!(inputs[5], i32); // num of cyborgs in troop
            let arg_5 = parse_input!(inputs[6], i32); // turns until arrival


            if entity_type == "TROOP" {
                troops.push(Troop {
                    id: entity_id,
                    owner: arg_1,
                    pop: arg_4,
                    from: arg_2,
                    target: arg_3,
                    turns: arg_5,
                });
            }else if entity_type == "FACTORY" {
                factories.push(Factory {
                    id: entity_id,
                    owner: arg_1,
                    population: arg_2,
                    prod: arg_3,
                });
            }else if entity_type == "BOMB" {

            }else {
                // should never happen
                unreachable!();
            }

        }

        if !do_move(&mut factories, &mut troops, &mut distances){
            println!("WAIT");
        }

    }
}


type Mov = i32;


fn mov_value(f1: &Factory, f2: &Factory, distance: i32) -> Mov {
    let mut value = (20-distance) * 5;
    value *= f1.population;
    if f2.owner == -1 {
       value -= (f2.prod * 5);
    }
    value /= (f2.population+1);

    value *= f2.prod+1;
    value *= f1.prod+1;
    print_err!("value {}", value);
    value
}

fn do_move(factories: &mut Vec<Factory>, troops: &mut Vec<Troop>, distances: &mut Vec<Vec<i32>> ) -> bool {

    let mut from = None;
    let mut to = None;
    let mut best = 0_32;
    let mut count = 0;

    for factory in factories.iter() {
        for factory2 in factories.iter() {
            if factory.owner == 1 && factory2.owner != 1 && factory.population > 2 {
                let value = mov_value(&factory, &factory2, distances[factory.id][factory2.id]);
                if value > best {
                    best = value;
                    from = Some(factory.id);
                    to = Some(factory2.id);
                    count = factory.population - 1;
                }
            }
        }
    }

    if to.is_none() || from.is_none() {
        return false;
    }

    println!("MOVE {} {} {}", from.unwrap(), to.unwrap(), count);
    true
}
