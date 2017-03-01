use std::io;


const GROUPING_CONSTANT: i32 = 5;
const TROOPS_CONSTANT: i32 = 5;
const FACTORY_VALUE_CONSTANT: i32 = 5;
const PRODUCTION_CONSTANT: i32 = 5;


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
    from: usize,
    target: usize,
    turns: i32,
}

#[derive(Debug, Clone, Copy)]
struct Bomb {
    id: usize,
    owner: i8,
    from: usize,
    target: usize,
    turns: i32
}


#[derive(Debug)]
struct State {
    factories: Vec<Factory>,
    troops: Vec<Troop>,
    bombs: Vec<Bomb>,
    my_bombs: i32,
    enemy_bombs: i32,
}

impl Factory {
    fn new(id: usize, owner: i8, population: i32, prod: i32) -> Self {
        Factory{
            id: id,
            owner: owner,
            population: population,
            prod: prod,
        }
    }

    fn bomb_result(&self) -> i32 {
        if self.population > 21 {
            self.population / 2
        }else{
            10
        }
    }

    fn bomb(&mut self) {
        self.population -= self.bomb_result();
    }
}

impl Troop {
    fn new(id: usize, owner: i8, pop: i32, from: usize, target: usize, turns: i32) -> Self {
        Troop{
            id: id,
            owner: owner,
            pop: pop,
            from: from,
            target: target,
            turns: turns,
        }
    }
}

impl Bomb{
    fn new(id: usize, owner: i8, from: usize, target: usize, turns: i32) -> Self {
        Bomb {
            id: id,
            owner: owner,
            from: from,
            target: target,
            turns: turns,
        }
    }
}

impl State {
    fn new(factories: Vec<Factory>, troops: Vec<Troop>, bombs: Vec<Bomb>, my_bombs: i32, enemy_bombs: i32) -> Self {
        State {
            factories: factories,
            troops: troops,
            bombs: bombs,
            my_bombs: my_bombs,
            enemy_bombs: enemy_bombs,
        }
    }

    fn send_troops(&mut self, from: usize, target: usize, count: i32, distances: &Vec<Vec<i32>>){
        if self.factories[from].population < count {
            unreachable!();
        }
        self.factories[from].population -= count;
        self.troops.push(Troop::new(
            0, //id, assigned by engine so not relevant here
            1,
            count,
            from,
            target,
            distances[from][target]
        ));
    }

    fn evaluate(&mut self, distances: &Vec<Vec<i32>>) -> i32 {
        let mut my_total_troops = 0;
        let mut enemy_total_troops = 0;

        let mut my_total_production = 0;
        let mut enemy_total_production = 0;

        let mut my_total_factories = 0;
        let mut enemy_total_factories = 0;

        let mut my_grouping_count = 0;
        let mut enemy_grouping_count = 0;

        let mut my_factory_values = 0;
        let mut enemy_factory_values = 0;

        let mut total_group_amount = 0;

        for factory in self.factories.iter() {
            if factory.owner == 1 {
                my_total_troops += factory.population;
                my_total_factories += 1;
                my_total_production += factory.prod;

                my_factory_values += factory.prod * factory.population;
            } else {
                enemy_total_troops += factory.population;
                enemy_total_factories += 1;
                enemy_total_production += factory.prod;

                enemy_factory_values += factory.prod * factory.population;
            }

            for other_factory in self.factories.iter() {
                if factory.owner == other_factory.owner && factory.id != other_factory.id {
                    if factory.owner == 1 {
                        my_grouping_count += distances[factory.id][other_factory.id];
                    } else {
                        enemy_grouping_count += distances[factory.id][other_factory.id];
                    }
                }
                total_group_amount += distances[factory.id][other_factory.id];
            }
        }


        (my_total_troops - enemy_total_troops) * TROOPS_CONSTANT
        + (my_factory_values - enemy_factory_values) * FACTORY_VALUE_CONSTANT
        + (my_total_production - enemy_total_production) * PRODUCTION_CONSTANT
        + (total_group_amount -  my_grouping_count) * GROUPING_CONSTANT
    }

}

#[allow(unused_variables, dead_code)]
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

    // we need to keep track of which bombs we've seen
    let mut bombs_cur: Vec<Bomb> = Vec::with_capacity(4);

    let mut my_bombs = 2;
    let mut enemy_bombs = 2;

    let mut count = 0;

    // game loop
    loop {
        count += 1;
        let mut input_line = String::new();
        io::stdin().read_line(&mut input_line).unwrap();
        print_err!("{}", input_line);
        let entity_count = parse_input!(input_line, usize); // the number of entities (e.g. factories and troops)

        let mut factories = Vec::with_capacity(factory_count);
        let mut troops = Vec::with_capacity(entity_count - factory_count);
        let mut bombs = Vec::with_capacity(4);


        for i in 0..entity_count as usize {
            let mut input_line = String::new();
            io::stdin().read_line(&mut input_line).unwrap();
            let inputs = input_line.split(" ").collect::<Vec<_>>();
            let entity_id = parse_input!(inputs[0], usize);
            let entity_type = inputs[1].trim().to_string();
            let arg_1 = parse_input!(inputs[2], i8);  // owner -1, 0, or 1
            let arg_2 = parse_input!(inputs[3], usize); // num of cyborgs or where troop leaves from
            let arg_3 = parse_input!(inputs[4], usize); // fact prod 0-3 or where troop headed
            let arg_4 = parse_input!(inputs[5], i32); // num of cyborgs in troop
            let arg_5 = parse_input!(inputs[6], i32); // turns until arrival

            if entity_type == "TROOP" {
                troops.push(Troop::new(
                    entity_id,
                    arg_1,
                    arg_4,
                    arg_2,
                    arg_3,
                    arg_5,
                ));
            }else if entity_type == "FACTORY" {
                factories.push(Factory::new(
                    entity_id,
                    arg_1,
                    arg_2 as i32,
                    arg_3 as i32,
                ));
            }else if entity_type == "BOMB" {
                bombs.push(Bomb::new(
                    entity_id,
                    arg_1,
                    arg_2,
                    arg_3,
                    arg_4,
                ));
            }else {
                // should never happen
                unreachable!();
            }
        }

        // this should only subtract bomb counts when it is a new bomb.
        for bomb in bombs.iter() {
            let mut i = -1;
            for (index, other_bomb) in bombs_cur.iter().enumerate() {
                if bomb.id == other_bomb.id {
                    i = index as i32;
                    break;
                }
            }
            if i == -1 {
                if bomb.owner == 1 {
                    my_bombs -= 1;
                }else{
                    enemy_bombs -= 1;
                }
                bombs_cur.push(bomb.clone());
            }
        }

        let state = State::new(factories, troops, bombs, my_bombs, enemy_bombs);

        if !do_move(state, &distances){
            println!("WAIT");
        }
    }
}


#[allow(unused_variables, dead_code)]
fn mov_value(f1: &Factory, f2: &Factory, distance: i32) -> i32 {
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



#[allow(unused_variables, dead_code)]
fn do_move(state: State, distances: &Vec<Vec<i32>> ) -> bool {

    let mut from = None;
    let mut to = None;
    let mut best = 0_32;
    let mut count = 0;

    let factories = state.factories;
    let troops = state.troops;

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
