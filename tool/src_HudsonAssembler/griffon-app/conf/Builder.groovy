root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}
root.'griffon.builder.swingxtras.SwingxtrasBuilder'.view = '*'

jx {
    'groovy.swing.SwingXBuilder' {
        view = '*'
    }
}
