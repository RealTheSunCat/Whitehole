/*
    Copyright 2012 Mega-Mario

    This file is part of Whitehole.

    Whitehole is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option)
    any later version.

    Whitehole is distributed in the hope that it will be useful, but WITHOUT ANY 
    WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
    FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along 
    with Whitehole. If not, see http://www.gnu.org/licenses/.
*/

package whitehole;

import whitehole.fileio.*;
import java.io.*;
import com.jogamp.opengl.util.Animator;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import whitehole.vectors.*;

/**
 *
 * @author lolol
 */
public class GalaxyEditorForm extends javax.swing.JFrame
{

    /**
     * Creates new form GalaxyEditorForm
     */
    public GalaxyEditorForm(String galaxy)
    {
        initComponents();

        galaxyName = galaxy;

        GLCanvas glc = new GLCanvas();
        glc.addGLEventListener(new GalaxyRenderer());
        
        pnlGLPanel.setLayout(new BorderLayout());
        pnlGLPanel.add(glc, BorderLayout.CENTER);
        pnlGLPanel.doLayout();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGLPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout pnlGLPanelLayout = new javax.swing.GroupLayout(pnlGLPanel);
        pnlGLPanel.setLayout(pnlGLPanelLayout);
        pnlGLPanelLayout.setHorizontalGroup(
            pnlGLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );
        pnlGLPanelLayout.setVerticalGroup(
            pnlGLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );

        getContentPane().add(pnlGLPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        //
    }//GEN-LAST:event_formWindowOpened

    
    public class GalaxyRenderer implements GLEventListener
    {
        private Bmd testmodel;
        private BmdRenderer testrenderer;
        private Renderer.RenderInfo renderinfo;
        
        
        @Override
        public void init(GLAutoDrawable glad)
        {
            GL2 gl = glad.getGL().getGL2();
            
            renderinfo = new Renderer.RenderInfo();
            renderinfo.drawable = glad;
            renderinfo.renderMode = Renderer.RenderMode.OPAQUE;
            
            try { 
                String objname = "HeavenlyBeachPlanet";
                RarcFilesystem arc = new RarcFilesystem(Whitehole.game.filesystem.openFile("/ObjectData/"+objname+".arc"));
                testmodel = new Bmd(arc.openFile("/"+objname+"/"+objname+".bdl")); 
            } catch (IOException ex) {}
            testrenderer = new BmdRenderer(renderinfo, testmodel);
            
            //gl.glClearColor(0f, 1f, 0f, 1f);
            gl.glFrontFace(GL2.GL_CW);
        }

        @Override
        public void dispose(GLAutoDrawable glad)
        {
            GL2 gl = glad.getGL().getGL2();
        }

        @Override
        public void display(GLAutoDrawable glad)
        {
            GL2 gl = glad.getGL().getGL2();
            
            gl.glClearColor(0f, 0f, 0.125f, 1f);
            gl.glClearDepth(1f);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
            
            Matrix4 mv = Matrix4.lookAt(new Vector3(1f, 1f, 1f), new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f));
            
            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glLoadMatrixf(mv.m, 0);
            gl.glScalef(0.0002f, 0.0002f, 0.0002f);
            
            gl.glEnable(GL2.GL_TEXTURE_2D);
            
            renderinfo.drawable = glad;
            testrenderer.render(renderinfo);
            
            gl.glUseProgram(0);
            gl.glDisable(GL2.GL_TEXTURE_2D);
            
            gl.glBegin(GL2.GL_LINES);
            gl.glColor4f(1f, 0f, 0f, 1f);
            gl.glVertex3f(0f, 0f, 0f);
            gl.glVertex3f(100000f, 0f, 0f);
            gl.glColor4f(0f, 1f, 0f, 1f);
            gl.glVertex3f(0f, 0f, 0f);
            gl.glVertex3f(0, 100000f, 0f);
            gl.glColor4f(0f, 0f, 1f, 1f);
            gl.glVertex3f(0f, 0f, 0f);
            gl.glVertex3f(0f, 0f, 100000f);
            gl.glEnd();
            
            glad.swapBuffers();
        }

        @Override
        public void reshape(GLAutoDrawable glad, int x, int y, int width, int height)
        {
            GL2 gl = glad.getGL().getGL2();
            
            //gl.setSwapInterval(1);
            gl.glViewport(x, y, width, height);
            
            float aspectRatio = (float)width / (float)height;
            gl.glMatrixMode(GL2.GL_PROJECTION);
            gl.glLoadIdentity();
            float ymax = 0.01f * (float)Math.tan(0.5f * (float)((70f * Math.PI) / 180f));
            gl.glFrustum(
                    -ymax * aspectRatio, ymax * aspectRatio,
                    -ymax, ymax,
                    0.01f, 1000f);
        }
    }
    
   
    public String galaxyName;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlGLPanel;
    // End of variables declaration//GEN-END:variables
}
