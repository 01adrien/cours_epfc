set search_path = bdge, bdlivre;

-- EX 1
-- Ecrivez une fonction scalaire qui permet de trouver le nombre de traducteurs ou écrivains de la BD (tous).

create or replace function ex1()
    returns integer as
$$
declare
    num integer;
begin
    select count(*) from bdlivre.traducteur_ecrivain into num;
    return num;
end;
$$ language plpgsql;

select ex1();

-- EX 2
-- Ecrivez une fonction table qui liste les nom, prénom, nationalité des différents traducteurs de la BD.

create or replace function ex2()
    returns table(nom bdlivre.tnom, prenom bdlivre.tprenom , nat bdlivre.tlangue) as
$$
select nom, prenom, nat
from bdlivre.traducteur_ecrivain
$$ language sql;

-- EX 3
-- Ecrivez une fonction qui ressort le nombre de livres traduits par un traducteur X particulier
-- dont les nom et prénom (paire supposée unique (discriminante !)) sont fournis en paramètre.

create or replace function ex3(p bdlivre.tprenom, n bdlivre.tnom)
    returns integer as
$$
declare
    num integer;
begin
    select count(*) from bdlivre.traduit_par as tp
    join bdlivre.traducteur_ecrivain as te on te.idtrad_ecriv = tp.numtrad
    where lower (te.prenom) = lower(p) and lower(te.nom) = lower(n) into num;
    return num;
end;
$$ language plpgsql;

-- EX 4
-- Ecrivez une fonction qui ressort le nombre d’oeuvres d’un genre donné écrites par un auteur donné.

create or replace function ex4(g bdlivre.tgenre, n bdlivre.tnom)
    returns integer as
$$
declare
    num integer;
begin
    select count(*) from bdlivre.ecrit_par as ep
    join bdlivre.traducteur_ecrivain as te on te.idtrad_ecriv = ep.numecriv
    join bdlivre.oeuvre as o on o.idoeuvre = ep.numoeuvre
    where lower(te.nom) = lower(n) and lower(o.genre) = lower(g) into num;
    return num;
end;
$$ language plpgsql;

select ex4('informatique', 'guttag');


insert into bdlivre.ecrit_par(numoeuvre, numecriv) values (6, 2);

select * from bdlivre.traducteur_ecrivain;
select * from bdlivre.traduit_par;
select * from bdlivre.ecrit_par;
select * from bdlivre.oeuvre;

