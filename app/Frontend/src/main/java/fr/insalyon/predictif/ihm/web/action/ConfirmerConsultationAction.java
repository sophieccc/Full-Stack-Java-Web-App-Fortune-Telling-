/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.predictif.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zakaria
 */
public class ConfirmerConsultationAction extends Action {
    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean success = false;
        Service service = new Service();
        Long id = (Long) session.getAttribute("idEmploye");
        System.out.println("Employe "+ id);
        if(id!=null) {
            Employe e = service.rechercherEmployeParId(id);
            Consultation consul = service.getCurrentConsultation(e);//e.getConsultations().get(0);
            if (consul != null)
            {
                success = service.confirmConsultation(consul);
            }
        }else{
            request.setAttribute("notLoggedIn", true);
        }
        request.setAttribute("success", success); 
    }
}
