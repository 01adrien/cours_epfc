using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using prid_tuto.Models;

namespace prid_tuto.Controllers;

/*
Le contrôleur est instancié automatiquement par ASP.NET Core quand une requête HTTP est reçue.
Les deux paramètres du constructeur reçoivent automatiquement, par injection de dépendance, 
une instance du context EF (MsnContext) et une instance de l'auto-mapper (IMapper).
*/

[Route("api/[controller]")]
[ApiController]
public class MembersController(MsnContext context, IMapper mapper) : ControllerBase
{
    [HttpGet]
    public async Task<ActionResult<IEnumerable<MemberDTO>>> GetAll() {
        /*
        Remarque: La ligne suivante ne marche pas :
            return _mapper.Map<IEnumerable<MemberDTO>>(await _context.Members.ToListAsync());
        En effet :
            C# doesn't support implicit cast operators on interfaces. Consequently, conversion of the interface to a concrete type is necessary to use ActionResult<T>.
            See: https://docs.microsoft.com/en-us/aspnet/core/web-api/action-return-types?view=aspnetcore-5.0#actionresultt-type
        */

        // Récupère une liste de tous les membres et utilise le mapper pour les transformer en leur DTO
        return mapper.Map<List<MemberDTO>>(await context.Members.ToListAsync());
    }

    [HttpGet("{pseudo}")]
    public async Task<ActionResult<MemberDTO>> GetOne(string pseudo) {
        // Récupère en BD le membre dont le pseudo est passé en paramètre dans l'url
        var member = await context.Members.FindAsync(pseudo);
        // Si aucun membre n'a été trouvé, renvoyer une erreur 404 Not Found
        if (member == null)
            return NotFound();
        // Transforme le membre en son DTO et retourne ce dernier
        return mapper.Map<MemberDTO>(member);
    }

    [HttpPost]
    public async Task<ActionResult<MemberDTO>> PostMember(MemberWithPasswordDTO member) {
        // Utilise le mapper pour convertir le DTO qu'on a reçu en une instance de Member
        var newMember = mapper.Map<Member>(member);
        // Valide les données
        var result = await new MemberValidator(context).ValidateOnCreate(newMember);
        if (!result.IsValid)
            return BadRequest(result);
        // Ajoute ce nouveau membre au contexte EF
        context.Members.Add(newMember);
        // Sauve les changements
        await context.SaveChangesAsync();

        // Renvoie une réponse ayant dans son body les données du nouveau membre (3ème paramètre)
        // et ayant dans ses headers une entrée 'Location' qui contient l'url associé à GetOne avec la bonne valeur 
        // pour le paramètre 'pseudo' de cet url.
        return CreatedAtAction(nameof(GetOne), new { pseudo = member.Pseudo }, mapper.Map<MemberDTO>(newMember));
    }

    [HttpPut]
    public async Task<IActionResult> PutMember(MemberDTO dto) {
        // Récupère en BD le membre à mettre à jour
        var member = await context.Members.FindAsync(dto.Pseudo);
        // Si aucun membre n'a été trouvé, renvoyer une erreur 404 Not Found
        if (member == null)
            return NotFound();
        // Mappe les données reçues sur celles du membre en question
        mapper.Map<MemberDTO, Member>(dto, member);
        // Valide les données
        var result = await new MemberValidator(context).ValidateAsync(member);
        if (!result.IsValid)
            return BadRequest(result);
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
