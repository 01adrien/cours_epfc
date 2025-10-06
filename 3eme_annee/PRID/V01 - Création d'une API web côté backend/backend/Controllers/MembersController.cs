using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using prid_tuto.Models;

namespace prid_tuto.Controllers;

/*
Le contrôleur est instancié automatiquement par ASP.NET Core quand une requête HTTP est reçue.
Le paramètre du constructeur reçoit automatiquement, par injection de dépendance, 
une instance du context EF (MsnContext).
*/

[Route("api/[controller]")]
[ApiController]
public class MembersController(MsnContext context) : ControllerBase
{
    [HttpGet]
    public async Task<ActionResult<IEnumerable<Member>>> GetAll() {
        // Récupère une liste de tous les membres
        return await context.Members.ToListAsync();
    }

    [HttpGet("{pseudo}")]
    public async Task<ActionResult<Member>> GetOne(string pseudo) {
        // Récupère en BD le membre dont le pseudo est passé en paramètre dans l'url
        var member = await context.Members.FindAsync(pseudo);
        // Si aucun membre n'a été trouvé, renvoyer une erreur 404 Not Found
        if (member == null)
            return NotFound();
        // Retourne le membre
        return member;
    }

    [HttpPost]
    public async Task<ActionResult<Member>> PostMember(Member member) {
        // Vérifie s'il existe déjà un membre ayant le même pseudo que celui qu'on veut créer
        var other = await context.Members.FindAsync(member.Pseudo);
        // Si on trouve un tel membre, on renvoit une erreur
        if (other != null)
            return BadRequest(new { error = $"Member {member.Pseudo} already exists" });

        // Ajoute le nouveau membre au contexte EF
        context.Members.Add(member);
        // Sauve les changements
        await context.SaveChangesAsync();

        // Renvoie une réponse ayant dans son body les données du nouveau membre (3ème paramètre)
        // et ayant dans ses headers une entrée 'Location' qui contient l'url associé à GetOne avec la bonne valeur 
        // pour le paramètre 'pseudo' de cet url.
        return CreatedAtAction(nameof(GetOne), new { pseudo = member.Pseudo }, member);
    }

    [HttpPut]
    public async Task<IActionResult> PutMember(Member member) {
        // Vérifie si le membre à mettre à jour existe bien en BD
        var exists = context.Members.Any(m => m.Pseudo == member.Pseudo);
        // Si aucun membre n'a été trouvé, renvoyer une erreur 404 Not Found
        if (!exists)
            return NotFound();
        // Ajoute le membre reçu en paramètre au contexte et force son état à "Modified" pour qu'EF fasse un update
        context.Entry(member).State = EntityState.Modified;
        // Sauve les changements
        await context.SaveChangesAsync();
        // Retourne un statut 204 avec une réponse vide
        return NoContent();
    }

    [HttpDelete("{pseudo}")]
    public async Task<IActionResult> DeleteMember(string pseudo) {
        // Récupère en BD le membre à supprimer
        var member = await context.Members.FindAsync(pseudo);
        // Si aucun membre n'a été trouvé, renvoyer une erreur 404 Not Found
        if (member == null)
            return NotFound();
        // Indique au contexte EF qu'il faut supprimer ce membre
        context.Members.Remove(member);
        // Sauve les changements
        await context.SaveChangesAsync();
        // Retourne un statut 204 avec une réponse vide
        return NoContent();
    }
}
